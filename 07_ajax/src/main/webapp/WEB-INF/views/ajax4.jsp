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
	  fnImage();
  })
  
  function fnImage(){
	  $('#btn_image').click(function(){
		  var path = encodeURIComponent('D:\\GDJ69\\assets\\image');
		  var filename = $('#image').val();
		  $('#display').html('<img src="${contextPath}/ajax4/display.do?path=' + path + '&filename=' + filename + '" width="400px">');
	  });
  }
</script>
<body>
  
  <div>
    <select id="image">
      <c:forEach var="n" begin="1" end="10" step="1">
        <option>뉴진스${n}.jpg</option>
      </c:forEach>
    </select>
    <button id="btn_image">이미지</button>
    <div id="display"></div>
  </div>
  
</body>
</html>