<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />
    
<jsp:include page="../layout/header.jsp">
  <jsp:param value="내 페이지" name="title"/>
</jsp:include>

<script src="${contextPath}/resources/js/user_modify.js?dt=${dt}"></script>

<h2>내 페이지</h2>

<div>

  <div>
    <button type="button" id="btn_changePw">비밀번호변경</button>
  </div>
  <form id="frm_mypage" method="post">
  
    <div>이메일 : ${sessionScope.user.email}</div>
    <div>가입일 : ${sessionScope.user.joinedAt}</div><div>
    <div>
      <label for="name">이름</label>
      <input type="text" id="name" name="name" value="${sessionScope.user.name}">
    </div>
    <div>
      <div>성별</div>
      <input type="radio" id="man" name="gender" value="man">
      <label for="man">남자</label>
      <input type="radio" id="woman" name="gender" value="woman">
      <label for="woman">여자</label>
      <input type="radio" id="none" name="gender" value="none">
      <label for="none">선택안함</label>
    </div>
    <script>
    console.log('${sessionScope.user.gender}');
      $(':radio[value=${sessionScope.user.gender}]').prop('checked', true);
    </script>
    <div>
      <input type="text" name="postcode" id="postcode" value="${sessionScope.user.postcode}" readonly>
      <input type="button" onclick="execDaumPostcode()" value="우편번호 찾기"><br>
      <input type="text" name="roadAddress" id="roadAddress" value="${sessionScope.user.roadAddress}" readonly>
      <input type="text" name="jibunAddress" id="jibunAddress" value="${sessionScope.user.jibunAddress}" readonly><br>
      <input type="text" name="detailAddress" id="detailAddress" value="${sessionScope.user.detailAddress}">
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
            
            // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
            if(roadAddr !== ''){
              $('#extraAddress').val(extraRoadAddr);
            } else {
              $('#extraAddress').val('');
            }
          }
        }).open();
      }
    </script>
    
    <div>
      <label for="mobile">휴대전화(- 제외)</label>
      <input type="text" name="mobile" id="mobile" value="${sessionScope.user.mobile}" >
      <span id="msg_mobile"></span>
    </div>
    
    <div>
      <label for="event">이벤트 수신 동의(선택)</label>
      <input type="radio" id="event_on" name="event" value="on">
      <label for="event_on">동의함</label>
      <input type="radio" id="event_off" name="event" value="off">
      <label for="event_off">동의안함</label>
    </div>
    <div>
      <textarea>본 약관은...</textarea>
    </div>
    <script>
      if(${sessionScope.user.mobile} === '0') {
      	$('#event_off').prop('checked', true);
      } else {
        $('#event_on').prop('checked', true);
      }
    </script>
    
    <div>
      <input type="hidden" name="userNo" value="${sessionScope.user.userNo}">
      <button type="button" id="btn_modify">개인정보수정</button>
    </div>
    
  </form>
  
  <div>
    <button type="button" id="btn_leaveUser">회원탈퇴</button>
  </div>

</div>

<%@ include file="../layout/footer.jsp" %>
