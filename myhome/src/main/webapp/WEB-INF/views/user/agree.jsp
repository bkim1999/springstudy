<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />
    
<jsp:include page="../layout/header.jsp">
  <jsp:param value="약관동의" name="title"/>
</jsp:include>

<div>

<script src="${contextPath}/resources/js/user_agree.js"></script>
  
  <h2>약관동의</h2>
  
  <form method="get" action="${contextPath}/user/join.form" id="frm_agree">
    <div>
      <input type="checkbox" id="chk_all">
      <label for="chk_all">모두 동의합니다.</label>
    </div>
    
    <hr>
    
    <div>
      <input type="checkbox" id="service" name="service" class="chk_each">
      <label for="service">서비스 이용약관 동의(필수)</label>
    </div>
    <div>
      <textarea>본 약관은...</textarea>
    </div>
    <div>
      <input type="checkbox" id="event" name="event" class="chk_each">
      <label for="event">이벤트 수신 동의(선택)</label>
    </div>
    <div>
      <textarea>본 약관은...</textarea>
    </div>
    <div>
      <button>다음</button>
    </div>
  </form>
  
</div>

<%@ include file="../layout/footer.jsp" %>
