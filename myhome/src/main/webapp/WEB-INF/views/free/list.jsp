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

<style>
  .blind_write_tr {
    display: none;
  }
</style>

<h1>자유게시판</h1>

<div>

  <div>
    <a href="${contextPath}/free/write.form">
      <button type="button" class="btn btn-primary">새글작성</button>
   </a>    
  </div>
  
  <hr>
  
  <div class="">
    <table class="mx-auto table">
      <thead>
        <tr>
          <td>순번</td>
          <td>작성자</td>
          <td>내용</td>
          <td>작성일자</td>
        </tr>
      </thead>
      <tbody>
        <c:forEach items="${freeList}" var="free" varStatus="vs">
          <tr class="list">
            <td>${beginNo - vs.index}</td>
            <c:if test="${free.status == 1}">
              <td>${free.email}</td>
              <td>
                <c:forEach begin="1" end="${free.depth}">&nbsp;&nbsp;</c:forEach>
                <c:if test="${free.depth != 0}">
                  <i class="fa-brands fa-replyd"></i>
                </c:if>
                ${free.contents}
                <c:if test="${sessionScope.user != null}">
                  <button type="button" class="btn_reply">댓글</button>
                  <form class="frm_remove" method="post" action="${contextPath}/free/remove.do" style="display:inline">
                    <c:if test="${sessionScope.user.email == free.email}">
                      <input type="hidden" name="freeNo" value="${free.freeNo}">
                      <button type="submit">삭제</button>
                    </c:if>
                  </form>
                </c:if>
              </td>
              <td>${free.createdAt}</td>
            </c:if>
            <c:if test="${free.status == 0}">
              <td colspan="3">삭제된 게시글입니다.</td>
            </c:if>
          </tr>
          <tr class="blind_write_tr">
            <td colspan="4">
              <form method="post" action="${contextPath}/free/addReply.do">
                <div>
                  <label for="email">작성자</label>
                  <input type="text" name="email" id="email" value="${sessionScope.user.email}" readonly>
                </div>
                <div>
                  <label for="contents">내용</label>
                  <input type="text" name="contents" id="contents">
                </div>
                <div><button>작성</button></div>
                <input type="hidden" name="depth" value="${free.depth}">
                <input type="hidden" name="groupNo" value="${free.groupNo}">
                <input type="hidden" name="groupOrder" value="${free.groupOrder}">
              </form>
            </td>
          </tr>
        </c:forEach>
      </tbody>
      <tfoot class="d-flex justify-content-between">
        <tr>
          <td colspan="4">${paging}</td>
        </tr>
    </table>
  </div>
  <div>
    <form method="get" action="${contextPath}/free/search.do">
      <select name="column">
        <option value="EMAIL">작성자</option>
        <option value="CONTENTS">내용</option>
      </select>
      <input type="text" name="query" placeholder="검색어 입력" value="${query}">
      <button type="submit" class="btn btn-outline-primary">검색</button>
    </form>
  </div>
  
</div>

<script>
  
  
  const fnAddResult = () => {
    let addResult = '${addResult}';
    if(addResult != ''){
      if(addResult === '1'){
        alert('게시글이 등록되었습니다.');
      } else {
        alert('게시글이 등록되지 않았습니다.');
      }
    }
  }
  
  const fnAddReplyResult = () => {
	    let addReplyResult = '${addReplyResult}';
	    if(addReplyResult != ''){
	      if(addReplyResult === '1'){
	        alert('댓글이 등록되었습니다.');
	      } else {
	        alert('댓글이 등록되지 않았습니다.');
	      }
	    }
	  }
  
  const fnBlind = () => {
	  $('.btn_reply').click((ev) => {
  		  let writeForm = $(ev.target).parents('.list').next();
  		  if(writeForm.hasClass('blind_write_tr')){
  		    writeForm.removeClass('blind_write_tr');
  		  } else {
  			  writeForm.addClass('blind_write_tr');
  		  }
	  });
  }
  
  const fnRemove = () => {
	  $('.frm_remove').submit((ev) => {
      if(!confirm('정말 삭제하시겠습니까?')){
    	  ev.preventDefault();
    	  return;
      }
    });
  }
  
  const fnRemoveResult = () => {
	  let removeResult = '${removeResult}';
    if(removeResult != ''){
      if(removeResult === '1'){
        alert('게시글이 삭제되었습니다.');
      } else {
        alert('게시글이 삭제되지 않았습니다.');
      }
    }
  }

  fnAddResult();
  fnAddReplyResult();
  fnBlind();
  fnRemove();
  fnRemoveResult();

</script>

<%@ include file="../layout/footer.jsp" %>
