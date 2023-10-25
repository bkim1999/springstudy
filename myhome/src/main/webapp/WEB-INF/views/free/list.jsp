<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />
    
<jsp:include page="../layout/header.jsp">
  <jsp:param value="자유게시판" name="title"/>
</jsp:include>

<h1>자유게시판</h1>

<div>

  <div><a href="${contextPath}/free/write.form">새글작성</a>    
  
  <hr>
  
  <div>
    <table border="1">
      <tbody>
        <c:forEach items="${freeList}" var="free" varStatus="vs">
          <tr>
            <td>${beginNo - vs.index}</td>
            <td>${free.email}</td>
            <td>${free.contents}</td>
            <td>${free.createdAt}</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
  
  <div>${paging}</div>

</div>

<%@ include file="../layout/footer.jsp" %>
