<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />
    
<jsp:include page="../layout/header.jsp">
  <jsp:param value="게시글 작성" name="title"/>
</jsp:include>

<script src=""></script>
  
<h1>게시글 작성</h1>

<div>

  <form method="post" action="${contextPath}/free/add.do" id="frm_free_add">
    <div>
      <label for="email">작성자</label>
      <input type="text" name="email" id="email" value="${sessionScope.user.email}" readonly>
    </div>
    <div>
      <label for="contents">내용</label>
      <textarea name="contents" id="contents"></textarea>
    </div>
    <div>
      <button>작성완료</button>
    </div>
    
  </form>

</div>

<%@ include file="../layout/footer.jsp" %>
