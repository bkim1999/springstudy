<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>초특급속보</title>
</head>
<body>
  <div>제목: ${title}</div>
  속보) 강해린, 그녀는 신인가? 신, 그는 <a href="${contextPath}/article/main.do">강해린</a>인가?
  
  <br>
  
  <a href="${contextPath}/article/confirm.do">세션확인하기</a>
</body>
</html>