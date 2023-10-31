<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />
    
<jsp:include page="../layout/header.jsp">
  <jsp:param value="로그인" name="title"/>
</jsp:include>

<div>

  <form method="post" action="${contextPath}/user/login.do">
    <div>
      <label for="email">아이디</label>
      <input type="text" name="email" id="email" placeholder="admin@gmail.com">
    </div>
    <div>
      <label for="pw">비밀번호</label>
      <input type="password" name="pw" id="pw">
    </div>
    <div>
      <input type="hidden" name="referer" value="${referer}">
      <button class="btn btn-success">로그인</button>
    </div>
    <div>
      <input type="checkbox" name="chk_auto_login" id="chk_auto_login">
      <label for="chk_auto_login">자동로그인</label>
    </div>
  </form>
  
  <hr>
  
  <div>
    <a href="${url}"><img src="${contextPath}/resources/image/btnG_축약형.png" width="100px"></a>
  </div>
</div>

<%@ include file="../layout/footer.jsp" %>
