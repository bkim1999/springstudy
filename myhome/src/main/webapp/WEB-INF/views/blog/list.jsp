<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />

<jsp:include page="../layout/header.jsp">
  <jsp:param value="블로그" name="title"/>
</jsp:include>

<div>

  <div>
    <a href="${contextPath}/blog/write.form">
      <button type="button" class="btn btn-primary">새글작성</button>
    </a>
  </div>
  
  <hr>
  
  <div>
    <table class="table">
      <thead>
        <tr>
          <td>순번</td>
          <td>제목</td>
          <td>조회수</td>
          <td>작성자</td>
          <td>작성일</td>
        </tr>
      </thead>
      <tbody>
        <c:forEach items="${blogList}" var="b" varStatus="vs">
          <tr>
            <td>${beginNo - vs.index}</td>
            <td>
              <c:if test="${sessionScope.user.userNo == b.userDto.userNo}">
                <a href="${contextPath}/blog/detail.do?blogNo=${b.blogNo}">${b.title}</a>
              </c:if>
              <c:if test="${sessionScope.user.userNo != b.userDto.userNo}">
                <a href="${contextPath}/blog/increaseHit.do?blogNo=${b.blogNo}">${b.title}</a>
              </c:if>
            </td>
            <td>${b.hit}</td>
            <td>${b.userDto.email}</td>
            <td>${b.createdAt}</td>
          </tr>
        </c:forEach>
      </tbody>
      <tfoot>
        <tr>
          <td colspan="5">${paging}</td>
        </tr>
      </tfoot>
    </table>
  </div> 
   

</div>
<%@ include file="../layout/footer.jsp" %>