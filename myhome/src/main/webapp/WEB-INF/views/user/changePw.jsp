<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />
    
<jsp:include page="../layout/header.jsp">
  <jsp:param value="내 페이지" name="title"/>
</jsp:include>

<script src="${contextPath}/resources/js/user_changePw.js?dt=${dt}"></script>

<h2>내 페이지</h2>

<div>

  <form method="post" action="${contextPath}/user/changePw.do" id="frm_changePw">
    <div>
      <div>
        <label for="pw">새로운 비밀번호</label>
        <input type="password" name="pw" id="pw">
        <span id="msg_pw"></span>
      </div>
      <div>
        <label for="pw_check">새로운 비밀번호 확인</label>
        <input type="password" id="pw_check">
        <span id="msg_pw_check"></span>
      </div>
    </div>
    <div>
      <input type="hidden" name="userNo" value="${sessionScope.user.userNo}">
      <button>비밀번호 변경</button>
    </div>
  </form>

</div>

<%@ include file="../layout/footer.jsp" %>
