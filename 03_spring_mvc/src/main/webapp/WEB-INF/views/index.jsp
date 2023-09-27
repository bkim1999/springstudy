<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
  
  <div>
    <a href='${contextPath}/board/list.do'>보드 목록</a>
  </div>
  
  <div>
    <a href='${contextPath}/notice/list.do'>notice 목록</a>
    <a href='${contextPath}/member/list.do'>member 목록</a>
  </div>
  
  <div>
    <a href="${contextPath}/blog/detail.do?blogNo=100">blog 100</a>
  </div>
  
  <div>
    <a href="${contextPath}/article/add.do?title=속보강해린여신">속보</a>
  </div>
  ${title}
  ${sessionScope.title}
</body>
</html>