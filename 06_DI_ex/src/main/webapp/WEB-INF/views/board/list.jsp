<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
  .board {
    padding: 10px ;
  }
</style>
</head>
<body>
  
  <div>
    <h3>게시글 목록</h3>
    <c:forEach items="${boardList}" var="boardDto">
      <div class="board">
        <div>
          <a href="${contextPath}/board/detail.do?boardNo=${boardDto.boardNo}">${boardDto.boardNo}</a>
        </div>
        <div>제목 : ${boardDto.title}</div>
        <div>작성자 : ${boardDto.editor}</div>
        <div><image src="${contextPath}/resources/image/${boardDto.imageName}" width="200px"></div>
      </div>
    </c:forEach>    
  </div>
  
</body>
</html>