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
    <div>작성자: ${blog.userDto.email}</div> 
    <div>조회수: ${blog.hit}</div>
    <div>IP 주소: ${blog.ip}</div>
    <div>작성일: ${blog.createdAt}</div>
    <div>수정일: ${blog.modifiedAt}</div>
    <div>
      <c:if test="${sessionScope.user.userNo == blog.userDto.userNo}">
        <form id="frm_btn" method="post">
          <button type="button" class="btn btn-outline-warning" id="btn_edit"><i class="fa-regular fa-pen-to-square"></i></button>
          <button type="button" class="btn btn-outline-danger" id="btn_remove"><i class="fa-solid fa-xmark"></i></button>
          <input type="hidden" name="blogNo" value="${blog.blogNo}">
          <input type="hidden" name="title" value="${blog.title}">
          <input type="hidden" name="contents" value='${blog.contents}'>
        </form>
      </c:if>
    </div>
    <div id="contents">${blog.contents}</div>
  </div>
  <script>
  
    var frmBtn = $('#frm_btn');
    
    const fnEditBlog = () => {
      $('#btn_edit').click(() => {
        frmBtn.attr('action', '${contextPath}/blog/edit.form');
        frmBtn.submit();
      });
    }
    
    const fnRemoveBlog = () => {
      $('#btn_remove').click(() => {
    	  if(confirm('이 게시물을 댓글과 함께 삭제하시겠습니까?')){
	        frmBtn.attr('action', '${contextPath}/blog/remove.do');
	        frmBtn.submit();
    	  }
      });
    }
    
    fnEditBlog();
    fnRemoveBlog();
    
  </script>
   
  <hr>
  
  <div>
    <form id="frm_comment_add">
      <textarea rows="3" cols="50" name="contents" id="contents" placeholder="댓글을 입력하세요."></textarea>
      <input type="hidden" name="userNo" value="${sessionScope.user.userNo}">
      <input type="hidden" name="blogNo" value="${blog.blogNo}">
      <button type="button" class="btn btn-outline-primary btn-sm" id="btn_comment_add"><i class="fa-solid fa-arrow-right-to-bracket"></i></button>
    </form>
  </div>
  
  <hr>
  
  <div id="comments_list"></div>  
  <div id="paging"></div>
  <style>
    .blind {
      display: none;
    }
  </style>
  <script>
    const fnRequiredLogin = () => {
      $('#contents, #btn_comment_add, .btn_add_reply, .btn_remove_reply').click(() => {
        if('${sessionScope.user}' === ''){
          if(confirm('로그인이 필요한 기능입니다. 로그인할까요?')){
            location.href = '${contextPath}/user/login.form';
          } else{
            return;
          }
        }
      });
    }
    
    const fnAjaxPaging = (p) => {
      page = p;
      fnCommentList();
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
              str += '<div style="border-bottom: 1px solid gray; margin-left:32px">';
            }
        	  if(c.status === 1){
          	  str += '  <div>' + c.userDto.name + '</div>';
          	  str += '  <div>' + c.contents + '</div>';
          	  str += '  <div style="font-size: 12px;">' + c.createdAt + '</div>';
          	  if(c.depth === 0){
          		  str += '  <div class="btn-group">'
          		  str += '    <button type="button" class="btn btn-outline-primary btn-sm btn_open_reply"><i class="fa-regular fa-comment"></i></button>';
                str += '    <input type="hidden" name="commentNo" value="' + c.commentNo + '">';
                // 댓글 [삭제] 버튼은 작성자에게만 보이게 한다.
                if('${sessionScope.user.userNo}' == c.userDto.userNo){
                	str += '    <button type="button" class="btn btn-outline-danger btn-sm btn_remove_reply"><i class="fa-solid fa-xmark"></i></button>';
                }
          		  str += '  </div>';
          		  str += '  <div class="wrap_add_reply blind">';
          		  str += '    <form class="frm_add_reply">';
          		  str += '      <textarea rows="3" cols="50" name="replyContents" placeholder="댓글을 입력하세요."></textarea>';
          		  str += '      <input type="hidden" name="userNo" value="${sessionScope.user.userNo}">';
          		  str += '      <input type="hidden" name="blogNo" value="${blog.blogNo}">';
          		  str += '      <input type="hidden" name="groupNo" value="' + c.groupNo + '">';
          		  str += '      <button type="button" class="btn btn-outline-primary btn_add_reply"><i class="fa-solid fa-arrow-right-to-bracket"></i></button>';
          		  str += '    </form>';
          		  str += '  </div>';
          	  }
          	  else{
                str += '  <div>'
                str += '    <input type="hidden" name="commentNo" value="' + c.commentNo + '">';
                str += '    <button type="button" class="btn btn-outline-danger btn-sm btn_remove_reply" value="<i class="fa-solid fa-xmark"></i>"></button>';
                str += '  </div>'
          	  }
        	  } else {
        		  str += '삭제된 댓글입니다.';
        	  }
            str += '</div>';
            $('#comments_list').append($(str));
          });
          $('#paging').append($(resData.paging));
        }
      });
    }
    
    const fnOpenReply = () => {
    	$(document).on('click', '.btn_open_reply', (ev) => {
    		var blind = $(ev.target).parents('.btn-group').next('.wrap_add_reply');
    		if(blind.hasClass('blind')) {
    			blind.removeClass('blind');
    		} else {
    			blind.addClass('blind');
    		}
    	});
    }
    
    const fnAddReply = () => {
    	$(document).on('click', '.btn_add_reply', (ev) => {
        $.ajax({
        	type: 'post',
        	url: '${contextPath}/blog/addCommentReply.do',
        	data: $(ev.target.closest('.frm_add_reply')).serialize(),
        	dataType: 'json',
        	success: (resData) => {
        		if(resData.addCommentReplyResult === 1){
              alert('대댓글이 등록되었습니다.');
              fnCommentList();
            } else {
              alert('대댓글이 등록되지 않았습니다.');
            }
        	}
        })
      });
    }
    
    const fnRemoveReply = () => {
      $(document).on('click', '.btn_remove_reply', (ev) => {
        $.ajax({
          type: 'post',
          url: '${contextPath}/blog/removeComment.do',
          data: 'commentNo=' + $(ev.target).closest('button').prev().val(),
          dataType: 'json',
          success: (resData) => {
            if(resData.removeCommentResult === 1){
              alert('답글이 삭제되었습니다.');
              fnCommentList();
            } else {
              alert('답글이 삭제되지 않았습니다.');
            }
          }
        })
      });
    }

    fnCommentList();
    fnRequiredLogin();
    fnCommentAdd();
    fnOpenReply();
    fnAddReply();
    fnRemoveReply();
    
  </script>
  
</div>
<%@ include file="../layout/footer.jsp" %>