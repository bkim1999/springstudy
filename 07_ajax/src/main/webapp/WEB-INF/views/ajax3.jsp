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
			  url: '${contextPath}/ajax3/list.do',
			  dataType: 'json',
			  success: function(resData){
				  $('#list').html('');
				  $.each(resData, function(i, elem){
					  $('#list').append('<div class="row" data-name="' + elem.name + '">' + elem.name + ', ' + elem.age + '세</div>');
				  });
			  }
		  });
	  });
  }
  
  function fnDetail(){
	  $(document).on('click', '.row', function(){
		  $.ajax({
			  type: 'post',
			  url: '${contextPath}/ajax3/detail.do',
			  contentType: 'application/json',
			  data: JSON.stringify({
				        'name': $(this).data('name')
				      }),
			  dataType: 'json',
			  success: function(resData){
				  $('#detail').html('');
				  $('#detail').append('<div>' + resData.name + ', ' + resData.age + '세. 그녀는 신인가?</div>');
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