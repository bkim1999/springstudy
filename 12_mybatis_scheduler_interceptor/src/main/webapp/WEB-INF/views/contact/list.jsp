<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리스트</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script>   

  $(function(){
	 fnAddResult(); 
	 fnDeleteResult();
  });
  
  function fnAddResult(){
	  var addResult = '${addResult}';
	  if(addResult !== ''){
		  if(addResult === '1'){
			  alert('성공!');
		  } else{
			  alert('실패!');
		  }
	  }
  }
  
  function fnDeleteResult(){
	    var deleteResult = '${deleteResult}';
	    if(deleteResult !== ''){
	      if(deleteResult === '1'){
	        alert('성공!');
	      } else{
	        alert('실패!');
	      }
	    }
	  }

</script>
<body>
  <div>
    <h3>연락처 목록</h3>
    <div>
      <a href="${contextPath}/contact/write.do">추가</a>
    </div>
    <table border="1">
      <thead>
        <tr>
          <td>번호</td>
          <td>이름</td>
          <td>전화번호</td>
        </tr>
      <tbody>
        <c:forEach items="${contactList}" var="c">
          <tr>
            <td>${c.contactNo}</td>
            <td><a href="${contextPath}/contact/detail.do?contactNo=${c.contactNo}">${c.name}</a></td>
            <td>${c.tel}</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</body>
</html>