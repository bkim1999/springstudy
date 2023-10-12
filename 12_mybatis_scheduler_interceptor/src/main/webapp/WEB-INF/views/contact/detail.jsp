<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리스트</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script>   
  $(function(){
	  fnModifyResult();
	  fnDeleteContact();
	  fnListContact();
  });
  
  function fnModifyResult(){
	  var modifyResult = '${modifyResult}';
	  if(modifyResult !== ''){
		  if(modifyResult === '1'){
			  alert('성공!');
		  } else{
			  alert('실패!');
		  }
	  }
  }
  
  function fnDeleteContact(){
	  $('#btn_delete').click(function(){
		  if(confirm('연락처를 삭제할까요?')){
			  $('#frm_detail').attr('action', '${contextPath}/contact/delete.do');
			  $('#frm_detail').submit();
		  }
	  });
	}
  
  function fnListContact(){
    $('#btn_list').click(function(){
      location.href='${contextPath}/contact/list.do';
    });
  }
  
</script>
<body>
  <div>
    <h3>상세 연락처</h3>
    <form method="post" action="${contextPath}/contact/modify.do" id="frm_detail">
      <div>
        <label for="name">이름</label>
        <input type="text" name="name" id="name" value="${contactDto.name}">
      </div>
      <div>
        <label for="tel">전화번호</label>
        <input type="text" name="tel" id="tel" value="${contactDto.tel}">
      </div>
      <div>
        <label for="email">이메일</label>
        <input type="text" name="email" id="email" value="${contactDto.email}">
      </div>
      <div>
        <label for="address">주소</label>
        <input type="text" name="address" id="address" value="${contactDto.address}">
      </div>
      <div>
        <label for="created_at">생성일</label>
        <input type="text" name="created_at" id="createdAt" value="${contactDto.createdAt}">
      </div>
      <div>
        <input type="hidden" name="contactNo" value="${contactDto.contactNo}">
        <button>수정</button>
        <button type="button" id="btn_list">목록</button>
        <button type="button" id="btn_delete">삭제</button>
        <button type="reset">초기화</button>
      </div>
    </form>
  </div>
</body>
</html>