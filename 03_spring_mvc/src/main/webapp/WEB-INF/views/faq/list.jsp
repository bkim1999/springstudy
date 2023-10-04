<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>강해린 리스트</title>
<script>
  
  var addResult = '${addResult}';
  
  if(addResult !== ''){
	  if(addResult === '1'){
		  alert('add 성공: 뉴진스 짱');
	  }
	  else{
		  alert('add 실패: 뉴진스 최고');
	  }
  }
  
</script>
</head>
<body>
  
  
</body>
</html>