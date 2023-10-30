<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />

<jsp:include page="../layout/header.jsp">
  <jsp:param value="${blog.blogNo}번 블로그" name="title"/>
</jsp:include>

<div>
  
  <div>
    <h1>${blog.title}</h1>
    <div>작성자: ${blog.userDto.name}</div> 
    <div>조회수: ${blog.hit}</div>
    <div>IP 주소: ${blog.ip}</div>
    <div>작성일: ${blog.createdAt}</div>
    <div>수정일: ${blog.modifiedAt}</div>
    <div></div>
    <div>${blog.contents}</div>
  </div> 
   
  <hr>
  
  <div>
    <form id="frm_comment_add">
      <textarea rows="3" cols="50" name="contents" id="contents" placeholder="댓글을 입력하세요."></textarea>
      <input type="hidden" name="userNo" value="${sessionScope.user.userNo}">
      <input type="hidden" name="blogNo" value="${blog.blogNo}">
      <button type="button" id="btn_comment_add">등록</button>
    </form>
  </div>
  
  <hr>
  
  <div id="comments_list"></div>  
  <div id="paging"></div>
  
  <script>
    const fnRequiredLogin = () => {
      $('#contents, #btn_comment_add').click(() => {
        if('${sessionScope.user}' === ''){
          if(confirm('로그인이 필요한 기능입니다. 로그인할까요?')){
            location.href = '${contextPath}/user/login.form';
          } else{
            return;
          }
        }
      });
    }
    
    const fnCommentAdd = () => {
      $('#btn_comment_add').click(() => {
        $.ajax({
          type: 'post',
          url: '${contextPath}/blog/addComment.do',
          data: $('#frm_comment_add').serialize(),
          dataType: 'json',
          success: (resData) => {
            if(resData.addCommentResult === 1){
              alert('댓글이 등록되었습니다.');
              fnCommentList();
            } else {
              alert('댓글이 등록되지 않았습니다.');
            }
          }
        })
      });
    }
    
    var page = 1;    
    
    const fnCommentList = () => {
          $.ajax({
            type: 'get',
            url: '${contextPath}/blog/commentList.do',
            data: 'page=' + page + '&blogNo=' + ${blog.blogNo},
            dataType: 'json',
            success: (resData) => {
            	$('#comments_list').empty();
              $('#paging').empty();
              if(resData.commentList.length === 0){
            	  $('#comments_list').text('첫 번째 댓글의 주인공이 되어보세요.');
            	  $('paging').text('');
            	  return;
              } 
              $.each(resData.commentList, (i, c) => {
            	  let str = '';
            	  if(c.depth === 0){
            		  str += '<div style="border-bottom: 1px solid gray;">';
            	  } else {
            		  str += '↳ <div style="border-bottom: 1px solid gray; margin-left:32px">';
            	  }
            	  str += '<div>' + c.userDto.name + '</div>';
            	  str += '<div>' + c.contents + '</div>';
            	  str += '<div style="font-size: 12px;">' + c.createdAt + '</div>';
            	  str += '</div>';
            	  $('#comments_list').append($(str));
              });
              $('#paging').append($(resData.paging));
            }
          })
      }
    
    const fnAjaxPaging = (p) => {
    	page = p;
    	fnCommentList();
    }

    fnCommentList();
    fnRequiredLogin();
    fnCommentAdd();
    
  </script>
  
</div>
<%@ include file="../layout/footer.jsp" %>