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
    fnListContact();
  });
  
  function fnListContact(){
    $('#btn_list').click(function(){
      location.href='${contextPath}/contact/list.do';
    });
  }
  
</script>
<body>
  <div>
    <h3>연락처 작성</h3>
    <form method="post" action="${contextPath}/contact/add.do">
      <div>
        <label for="name">이름</label>
        <input type="text" name="name" id="name">
      </div>
      <div>
        <label for="tel">전화번호</label>
        <input type="text" name="tel" id="tel">
      </div>
      <div>
        <label for="email">이메일</label>
        <input type="text" name="email" id="email">
      </div>
      <div>
        <label for="address">주소</label>
        <input type="text" name="address" id="address">
      </div>
      <div>
        <button>작성</button>
        <button type="button" id="btn_list">목록</button>
        <button type="reset">초기화</button>
      </div>
    </form>
  </div>
</body>
</html>