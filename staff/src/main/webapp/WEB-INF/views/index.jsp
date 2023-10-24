<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script>
  
  $(() => {
	  fnGetStaffList();
	  fnGetStaff();
	  fnRegisterStaff();
  });
  
  const fnInit = () => {
	  $('#sno').val('');
	  $('#name').val('');
	  $('#dept').val('');
  }
  
  const fnGetStaffList = () => {
	  $.ajax({
      type: 'get',
      url: '${contextPath}/staff/list.json',
      dataType: 'json',
      success: (resData) => {
        $('#staff_list').html('');
        $.each(resData, (i, staff) => {
      	  var tr = $('<tr>');
          tr.append('<td>' + staff.sno + '</td>');
          tr.append('<td>' + staff.name + '</td>');
          tr.append('<td>' + staff.dept + '</td>');
          tr.append('<td>' + staff.salary + '</td>');
          $('#staff_list').append(tr);
        });
      },
      error: (jqXHR) => {
    	  alert('전체 목록 조회에 실패하였습니다.');
        console.log(jqXHR);
      }
    });
  }
  
  const fnGetStaff = () => {
    $('#btn_query').click(() => {
      $.ajax({
        type: 'get',
        url: '${contextPath}/staff/query.json',
        data: 'query=' + $('#query').val(),
        dataType: 'json',
        success: (staff) => {
          $('#staff_list').html('');
          var tr = $('<tr>');
          tr.append('<td>' + staff.sno + '</td>');
          tr.append('<td>' + staff.name + '</td>');
          tr.append('<td>' + staff.dept + '</td>');
          tr.append('<td>' + staff.salary + '</td>');
          $('#staff_list').append(tr);
        },
        error: (jqXHR) => {
          alert('조회된 사원 정보가 없습니다.');
        }
      });
    });
  }
  
  const fnRegisterStaff = () => {
	  $('#btn_register').click(() => {
		  var namePassed = /^[0-9]{5}$/.test($('#sno').val());
		  var deptPassed = /^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]{3,5}$/.test($('#dept').val());
		  
		  if(!namePassed) {
			  alert('사원번호는 5자리 숫자입니다.');
		  }
		  if(!deptPassed) {
        alert('부서는 3~5자리 한글입니다.');
      }
		  if(namePassed && deptPassed) {
  		  $.ajax({
  			  type: 'post',
  			  url: '${contextPath}/staff/add.do',
  			  data: $('#frm_register').serialize(),
  		    dataType: 'json',
  		    success: (resData) => {
  		    	if(resData.addResult === 1){
  		    		alert('사원 등록에 성공하였습니다.');
  		    		fnGetStaffList();
  		    		fnInit();
  		    	} else {
  		    		alert('사원 등록에 실패하였습니다.');
  		    	}
  		    },
  		    error: (jqXHR) => {
  		    	if(jqXHR.responseJSON.addResult === 0){
              alert('사원 등록에 실패하였습니다.');
            };
  		    }
  		  });
	    }
		  
	  });
  }
  
</script>
</head>
<body>

  <div>
    <h1>사원등록</h1>
    <div>
      <form id="frm_register">
        <input type="text" name="sno" id="sno" placeholder="사원번호">
        <input type="text" name="name" id="name" placeholder="사원명">
        <input type="text" name="dept" id="dept" placeholder="부서명">
        <button type="button" id="btn_register">등록</button>
      </form>
    </div>  
  </div>
    
  <hr>
  
  <div>
    <h1>사원조회</h1>
    <div>
      <input type="text" name="query" id="query" placeholder="사원번호">
      <button type="button" id="btn_query">조회</button>        
      <button type="button" id="btn_list" onclick="javascript:fnGetStaffList()">전체</button>        
    </div>
  </div>
  
  <hr>
  
  <div>
    <h1>사원목록</h1>
    <div>
      <table border="1"> 
        <thead>
          <tr>
            <td>사원번호</td>
            <td>사원명</td>
            <td>부서명</td>
            <td>연봉</td>
          </tr>
        </thead>
        <tbody id="staff_list"></tbody>
      </table>
    </div>
  </div>
  
</body>
</html>