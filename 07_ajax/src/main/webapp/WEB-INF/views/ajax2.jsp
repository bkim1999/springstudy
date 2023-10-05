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
<script>
  $(function(){
	  fnList();
	  fnDetail();
  })
  
  function fnList(){
	  $('#btn_list').click(function(){
		  $.ajax({
			  type : 'get',
			  url: '${contextPath}/ajax2/list.do',
			  dataType: 'json',
			  success: function(resData){
				  $('#list').html('');
				  $.each(resData, function(i, elem){
					  $('#list').append('<div class="row">' + elem.name + ', ' + elem.age + '세</div><input type="hidden" value="' + elem.name + '">');
				  });
			  }
		  });
	  });
  }
  
  function fnDetail(){
	  $(document).on('click', '.row', function(){
		  $.ajax({
			  type: 'get',
			  url: '${contextPath}/ajax2/detail.do',
			  data: 'name=' + $(this).next('input').val(),
			  dataType: 'json',
			  success: function(resData){
				  $('#detail').html('');
          $('#detail').append('<image src="${contextPath}/resources/' + resData.name + '.jpg" width="200px">');
			  }
		  })
	  });
  }
</script>
<body>
  
  <div>
    <button id="btn_list">목록</button>
  </div>
  
  <hr>
  
  <div id="list"></div>
  
  <hr>
  
  <div id="detail"></div>
  
</body>
</html>