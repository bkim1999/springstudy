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
	  fnMemberRegister();
	  fnMemberList();
	  fnMemberDetail();
	  fnCheckAll();
	  fnCheckOne();
  });
  
  function fnInit(){
	  $('#id').val('');
	  $('#name').val('');
	  $('#goddess').prop('checked', true);
	  $('#address').val('뉴진스').prop('checked', true);

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
  			  fnAjaxPaging(1);
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
				  tr += '<td><input type="checkbox" class="chk_one" value="' + member.memberNo + '"></td>';
				  tr += '<td>' + member.id + '</td>';
				  tr += '<td>' + member.name + '</td>';
				  tr += '<td>' + member.gender + '</td>';
				  tr += '<td>' + member.address + '</td>';
				  tr += '<td><button type="button" class="btn_detail" data-memberNo="' + member.memberNo + '">조회</button></td>';
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
	  $('.btn_detail').click(function(){
		  $.ajax({
			  type: 'get',
	      url: '${contextPath}/members/page/' + page,
	      dataType: 'json',
	      success: function(resData){  
	      }
	    })
	  });
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
      <input type="radio" id="man" name="gender" value="man">
      <label for="man">남자</label>
      <input type="radio" id="woman" name="gender" value="woman">
      <label for="woman">여자</label>
      <input type="radio" id="goddess" name="gender" value="goddess" checked>
      <label for="goddess">여신</label>
    </div>
    <div>
      <label for="address">주소</label>
      <select id="address">
        <option selected>뉴진스</option>
        <option>아이브</option>
        <option>르세라핌</option>
      </select>
    </div>
    <div>
      <button type="button" onclick="fnInit()">초기화</button>
      <button type="button" id="btn_register">등록</button>
      <button type="button" id="btn_modify">수정</button>
    </div>
  </div>
  
  <hr>
  
  <div>
    <table border="1">
      <thead>
        <tr>
          <td><input type="checkbox" id="chk_all"><label for="chk_all">전체 선택</label></td>
          <td>아이디</td>
          <td>이름</td>
          <td>성별</td>
          <td>주소</td>
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