<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />
    
<jsp:include page="../layout/header.jsp">
  <jsp:param value="휴면해제" name="title"/>
</jsp:include>

<div>

<script src="${contextPath}/resources/js/user_agree.js"></script>
  
  <h2>휴면계정안내</h2>
  
  <div>
    안녕하세요. ${sessionScope.inactiveUser.email}님은 1년 이상 로그인 하지 않아 휴면회원으로 전환되었습니다.
  </div>
  <div>
    휴면전환일 : ${sessionScope.inactiveUser.inactivedAt}
  </div>
  <div>
    휴면해제를 원하시면 밑의 [휴면해제] 버튼을 클릭하세요.
  </div>
  <div>
    <button type="button" id="btn_active">휴면해제</button>
  </div>
  <script>
    const fnActive = () => {
    	$('#btn_active').click(() => {
    		location.href = '${contextPath}/user/active.do';
    	});
    }
    fnActive();
  </script>

<%@ include file="../layout/footer.jsp" %>
