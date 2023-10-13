<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<body>

  <div>
    <h3>MVC 파일첨부</h3>
    <form method="post" action="${contextPath}/upload.do" enctype="multipart/form-data">
      <div>
        <input type="file" name="files" class="files" multiple>
      </div>
      <div id="file_list">미리보기</div>
      <div>
        <button>업로드</button>
      </div>
    </form>
  </div>
  <script>
  </script>
  
  <hr>
  
  <div>
    <h3>ajax 파일첨부</h3>
    <div>
      <input type="file" class="files" multiple>
    </div>
    <div>
      <button type="button" id="btn_upload">업로드</button>
    </div>
  </div>
  <script>
  
    fnUpload();
    
    function fnUpload(){
    	$('#btn_upload').click(function(){
    		var formData = new FormData();
    		var files = $('.files')[0].files;
    		$.each(files, function(i, elem){
    	    formData.append('files', elem);
    		});
    		$.ajax({
    			type: 'post',
    			url: '${contextPath}/ajax/upload.do',
    			data: formData,
    			contentType: false,
    			processData: false,
    			dataType: 'json',
    			success: function(resData){
    				if(resData.success){
    					alert('성공');
    				} else{
    					alert('실패');
    				}
    			}
    		});
    		location.href = "${contextPath}/main.do";
    	});
    }
    
    fnFileCheck();
    
    function fnFileCheck(){
      $('.files').change(function(){
        $('#file_list').empty();
        var maxSize = 1024 * 1024 * 10;
        var maxSizePerFile = 1024 * 1024 * 1;
        var totalSize = 0;
        var files = this.files;
        for(let i = 0; i < files.length; i++){
          totalSize += files[i].size;
          if(files[i].size > maxSizePerFile){
            alert('각 첨부파일의 최대 크기는 10MB입니다.');
            $(this).val('');
            $('#file_list').empty();
            return;
          }
          $('#file_list').append('<div>' + files[i].name + '</div>');
        }
        if(totalSize > maxSize){
          alert('첨부파일의 최대 크기는 10MB이다.');
          $(this.files).val('');
          return;
        }
      });
    }
    
  </script>
</body>
</html>