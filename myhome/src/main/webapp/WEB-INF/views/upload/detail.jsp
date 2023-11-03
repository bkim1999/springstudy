<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />

<jsp:include page="../layout/header.jsp">
  <jsp:param value="${upload.uploadNo}번 첨부게시물" name="title"/>
</jsp:include>

<style>
  .attach {
    cursor: pointer;
  }
</style>

<div>

  <div>
  
    <div>
      <h1>제목: ${upload.title}</h1>
    </div>
    <div>
      <div id="email">작성자: ${upload.userDto.name}</div>
    </div>
    <div>
      <div id="contents">${upload.contents}</div>
    </div>
    
  </div>
  
  <hr>
  
  <div>
    <h2>첨부파일 다운로드</h2>
    
    <c:if test="${empty attachList}">
      <div>첨부 없음</div>
    </c:if>
    <c:if test="${not empty attachList}">
      <c:forEach items="${attachList}" var="atc">
        <div class="attach" data-attach_no="${atc.attachNo}">
          <c:if test="${atc.hasThumbnail == 1}">
            <img src="${contextPath}${atc.path}/s_${atc.filesystemName}" alt="썸네일">
          </c:if>
          <c:if test="${atc.hasThumbnail == 0}">
            <img src="${contextPath}/resources/image/뉴진스2.jpg" alt="썸네일">
          </c:if>
          ${atc.originalFilename}
        </div>
      </c:forEach>
      <div><a href="${contextPath}/upload/downloadAll.do?uploadNo=${upload.uploadNo}">모두 다운로드</a></div>
    </c:if>
   
  </div>
  
</div>
  
<script>

  const fnDownload = () => {
	  $('.attach').click(function(){
		  if(confirm('이 파일을 다운로드하시겠습니까?')){
			  location.href = '${contextPath}/upload/download.do?attachNo=' + $(this).data('attach_no');
		  }
		  
	  });
	  
  }
  
  fnDownload();
  
</script>
  
<%@ include file="../layout/footer.jsp" %>