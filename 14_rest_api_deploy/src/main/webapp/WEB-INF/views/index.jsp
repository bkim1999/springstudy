<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
  #paging a {
    padding: 10px;
  }
</style>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script>

  $(function(){
    fnInit();
    fnCheckAll();
    fnCheckOne();
    fnMemberRegister();
    fnMemberList();
    fnMemberDetail();
    fnMemberModify();
    fnMemberDelete();
    fnDeleteMembers();
  });
  
  function fnInit(){
    $('#memberNo').val('');
    $('#id').val('').prop('disabled', false);
    $('#name').val('');
    $(':radio[value=goddess]').prop('checked', true);
    $('#address').val('뉴진스');
    $('#btn_register').prop('disabled', false);
    $('#btn_modify').prop('disabled', true);
  }
  
  function fnCheckAll(){
    $('#chk_all').click(function(){
      $('.chk_one').prop('checked', $(this).prop('checked'));
    });
  }
  
  function fnCheckOne(){
    $(document).on('click', '.chk_one', function(){
      var total = 0;
      $.each($('.chk_one'), function(i, elem){
        total += $(elem).prop('checked');
      });
      $('#chk_all').prop('checked', total === $('.chk_one').length);
    })
  }
  
  function fnMemberRegister(){
    $('#btn_register').click(function(){
      $.ajax({
        type: 'post',
        url: '${contextPath}/members',
        contentType: 'application/json',
        data: JSON.stringify({
          id: $('#id').val(),
          name: $('#name').val(),
          gender: $(':radio:checked').val(),
          address: $('#address').val()
        }),
        dataType: 'json',
        success: function(resData){
          if(resData.addResult === 1){
            alert('회원 정보가 등록되었습니다.');
            fnAjaxPaging(1);
            fnInit();
          }
        },
        error: function(jqXHR){
          alert(jqXHR.responseText + '(에러코드:' + jqXHR.status + ')');
        }
      });
    });
  }
  
  var page = 1;
  
  function fnMemberList(){
    $.ajax({
      type: 'get',
      url: '${contextPath}/members/page/' + page,
      dataType: 'json',
      success: function(resData){
        
        $('#member_list').empty();
        $.each(resData.memberList, function(i, member){
          var tr = '<tr>';
          tr += '<td><input type="checkbox" class="chk_one" value="' + member.memberNo + '">' + member.memberNo + '</td>';
          tr += '<td>' + member.id + '</td>';
          tr += '<td>' + member.name + '</td>';
          tr += '<td>' + member.gender + '</td>';
          tr += '<td>' + member.address + '</td>';
          tr += '<td><button type="button" class="btn_detail" data-member_no="' + member.memberNo + '">조회</button></td>';
          tr += '<td><button type="button" class="btn_delete" data-member_no="' + member.memberNo + '">삭제</button></td>';
          tr += '</tr>';
          $('#member_list').append(tr);
        });

        $('#paging').empty();
        $('#paging').append(resData.paging);
        
      }     
    });
  }
  
  function fnAjaxPaging(p){
    page = p;
    $('#chk_all').prop('checked', false);   
    fnMemberList();
  }
  
  function fnMemberDetail(){
    $(document).on('click', '.btn_detail', function(){
      $.ajax({
        type: 'get',
        url: '${contextPath}/member/' + $(this).data('member_no'),
        dataType: 'json',
        success: function(resData){
          var member = resData.member;
          if(!member){
            alert('없는 멤버');
          } else{
              $('#memberNo').val(member.memberNo);
              $('#id').val(member.id);
              $('#id').prop('disabled', true);
              $('#name').val(member.name);
              $(':radio[value=' + member.gender + ']').prop('checked', true);
              $('#address').val(member.address);
              $('#btn_register').prop('disabled', true);
              $('#btn_modify').prop('disabled', false);
          }
        }
      })
    });
  }
  
  function fnMemberModify(){
    $(document).on('click', '#btn_modify', function(){
      $.ajax({
        type: 'put',
        url: '${contextPath}/member/',
        contentType: 'application/json',
        data: JSON.stringify({
          memberNo: $('#memberNo').val(),
          id: $('#id').val(),
          name: $('#name').val(),
          gender: $(':radio:checked').val(),
          address: $('#address').val(),
        }),
        dataType: 'json',
        success: function(resData){
          if(resData.modifyResult === 1){
            alert('회원 정보가 수정됨.');
            fnAjaxPaging(1);
          }
        }
      })
    });
  }
  
  function fnMemberDelete(){
    $(document).on('click', '.btn_delete', function(){
      if(confirm('정말 삭제???')){
        $.ajax({
          type: 'delete',
          url: '${contextPath}/member/' + $(this).data('member_no'),
          dataType: 'json',
          success: function(resData){
            console.log(resData);
            if(resData.deleteResult === 1){
              alert('삭제 완료.');
              fnAjaxPaging(1);
            }
          }
        });
      }
    });
  }
  
  function fnDeleteMembers(){
      $(document).on('click', '#btn_delete_list', function(){
        if(confirm('정말 삭제???')){
          var arr = [];
          $.each($('.chk_one:checked'), function(i, member){
            arr.push($(member).val());
          });
          console.log(arr);
          $.ajax({
            type: 'delete',
            url: '${contextPath}/members/' + arr.join(','),
            dataType: 'json',
            success: function(resData){
              if(resData.deleteResult >= 1){
                alert('삭제 완료.');
                fnAjaxPaging(1);
              }
            }
          });
        }
      });
    }
  
</script>
<body>
  <div>
    <h1>회원 관리</h1>
    <div>
      <label for="id">아이디</label>
      <input type="text" id="id">
    </div>
    <div>
      <label for="name">이름</label>
      <input type="text" id="name">
    </div>
    <div>
      <input type="radio" id="goddess" name="gender" value="goddess" checked>
      <label for="goddess">여신</label>
      <input type="radio" id="man" name="gender" value="man">
      <label for="man">남자</label>
      <input type="radio" id="woman" name="gender" value="woman">
      <label for="woman">여자</label>
    </div>
    <div>
      <label for="address">주소</label>
      <select id="address">
        <option selected>뉴진스</option>
        <option>르세라핌</option>
        <option>아이브</option>
      </select>
    </div>
    <input type="hidden" id="memberNo">
    <div>
      <button type="button" onclick="fnInit()">초기화</button>
      <button type="button" id="btn_register">등록</button>
      <button type="button" id="btn_modify">수정</button>
    </div>
  </div>
  
  <hr>
  
  <div>
    <button type="button" id="btn_delete_list">선택 삭제</button>
    <table border="1">
      <thead>
        <tr>
          <td><input type="checkbox" id="chk_all"><label for="chk_all">전체 선택</label></td>
          <td>아이디</td>
          <td>이름</td>
          <td>성별</td>
          <td>주소</td>
          <td></td>
          <td></td>
        </tr>
      </thead>
      <tbody id="member_list"></tbody>
      <tfoot>
        <tr>
          <td colspan="6" id="paging"></td>
        </tr>
      </tfoot>
    </table>
  </div>
  
</body>
</html>