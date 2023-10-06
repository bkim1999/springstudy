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
	   fnSearch();
   });
   
   function fnSearch(){
	   $('#btn_search').click(function(){
		   $.ajax({
			   type: 'get',
			   url: '${contextPath}/search.do',
			   data: $('#frm_shop').serialize(),
			   dataType: 'json',
			   success: function(resData){
				   fnTable(resData);
			   }
		   });
	   });
   }
   
   function fnTable(resData){
	   $('#product_table').empty();
	   var tbody = '<thead><tr><td>상품명</td><td>썸네일</td><td>최저가</td><td>판매처</td></tr></thead><tbody>';
	   
	   for(var i = 1; i < resData.display; i++){
		   var obj = resData.items[i];
		   tbody += '<tr>';
		   tbody += '<td><a href="' + obj.link + '">' + obj.title + '</a></td>';
		   tbody += '<td><a href="' + obj.link + '"><image src="' + obj.image + '" width="200px"></a></td>';
		   tbody += '<td>' + obj.lprice + '</td>';
		   tbody += '<td>' + obj.mallName + '</td>';
		   tbody += '</tr>'
	   }
	   tbody += '</tbody>';
	   $('#product_table').append(tbody);   
   }
</script>
<body>
  
  <div>
    
    <h1>쇼핑해버리기</h1>
    
    <form id="frm_shop" method="get">
    
      <div>
        <span>검색결과건수</span>
        <select name="display">
          <option>10</option>
          <option>50</option>
          <option>100</option>
        </select>
      </div>
      
      <div>
        <input type="radio" name="sort" id="sim" value="sim" checked="true">
        <label for="sim">유사도순</label>
        <input type="radio" name="sort" id="date" value="date">
        <label for="date">날짜순</label>
        <input type="radio" name="sort" id="asc" value="asc">
        <label for="asc">낮은가격순</label>
        <input type="radio" name="sort" id="dsc" value="dsc">
        <label for="dsc">높은가격순</label>
      </div>
      
      <div>
        <span>검색어 입력</span>
        <input type="text" name="query" value="뉴진스">
        <button type="button" id="btn_search">검색</button>
      </div>
     
    </form>
    
  </div>
  
  <hr>
  
  <div>
    <table border="1" id="product_table">
      <thead>
        <tr>
          <td>상품명</td>
          <td>썸네일</td>
          <td>최저가</td>
          <td>판매처</td>
        </tr>
      </thead>
     </table>
  </div> 
  
</body>
</html>