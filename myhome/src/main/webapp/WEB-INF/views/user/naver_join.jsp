<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />
    
<jsp:include page="../layout/header.jsp">
  <jsp:param value="네이버 간편가입" name="title"/>
</jsp:include>

<script>

  $(() => {
	  fnNaverJoin();
  });
  
  const fnNaverJoin = () => {
	  $('#frm_naver_join').submit((ev) => {
		  if(!$('#service').is(':checked')){
			  alert('필수 항목에 동의하세요.');
			  ev.preventDefault();
			  return;
		  }
	  })
  }
</script>
  
<h2>네이버 간편가입</h2>

<div>

  <form method="post" action="${contextPath}/user/naver/join.do" id="frm_naver_join">
    <div>
      <label for="email">이메일</label>
      <input type="text" name="email" id="email" value="${naverProfile.email}">
    </div>
    <div>
      <label for="name">이름</label>
      <input type="text" id="name" name="name" value="${naverProfile.name}" readonly>
      <span id="msg_name"></span>
    </div>
    <div>
      <div>성별</div>
      <input type="radio" id="man" name="gender" value="M">
      <label for="man">남자</label>
      <input type="radio" id="woman" name="gender" value="F">
      <label for="woman">여자</label>
    </div>
    <script>
      $(':radio[value=${naverProfile.gender}]').prop('checked', true);
    </script>
    
    <div>
      <input type="text" name="postcode" id="postcode" placeholder="우편번호" readonly>
      <input type="button" onclick="execDaumPostcode()" value="우편번호 찾기"><br>
      <input type="text" name="roadAddress" id="roadAddress" placeholder="도로명주소" readonly>
      <input type="text" name="jibunAddress" id="jibunAddress" placeholder="지번주소" readonly><br>
      <input type="text" name="detailAddress" id="detailAddress" placeholder="상세주소">
      
    </div>
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script>
      function execDaumPostcode(){
        new daum.Postcode({
        	oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var roadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ''; // 참고 항목 변수

            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
              extraRoadAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if(data.buildingName !== '' && data.apartment === 'Y'){
             extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if(extraRoadAddr !== ''){
              extraRoadAddr = ' (' + extraRoadAddr + ')';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            $('#postcode').val(data.zonecode);
            $('#roadAddress').val(roadAddr);
            $('#jibunAddress').val(data.jibunAddress);

          }
        }).open();
      }
    </script>
  
    <div>
      <label for="mobile">휴대전화</label>
      <input type="text" name="mobile" id="mobile" value="${naverProfile.mobile}" readonly>
      <span id="msg_mobile"></span>
    </div>
    
    <hr>
    
    <div>
      <input type="checkbox" id="service" name="service">
      <label for="service">서비스 이용약관 동의(필수)</label>
    </div>
    <div>
      <textarea>본 약관은...</textarea>
    </div>
    <div>
      <input type="checkbox" id="event" name="event">
      <label for="event">이벤트 수신 동의(선택)</label>
    </div>
    <div>
      <textarea>본 약관은...</textarea>
    </div>
    <div>
      <button>회원가입</button>
    </div>
    
  </form>

</div>

<%@ include file="../layout/footer.jsp" %>
