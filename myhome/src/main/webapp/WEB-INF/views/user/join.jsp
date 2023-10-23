<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />
    
<jsp:include page="../layout/header.jsp">
  <jsp:param value="회원가입" name="title"/>
</jsp:include>

<script src="${contextPath}/resources/js/user_join.js?dt=${dt}"></script>
  
<h2>회원가입</h2>

<div>

  <form method="post" action="${contextPath}/user/join.do" id="frm_join">
    <div>
      <label for="email">이메일</label>
      <input type="text" name="email" id="email" placeholder="admin@gmail.com">
      <button type="button" id="btn_get_code">인증코드 받기</button>
    </div>
    <div id="msg_email"> </div>
    <div>
      <input type="text" id="code" placeholder="인증코드 입력">
      <button type="button" id="btn_verify_code">인증하기</button>
    </div>
    <div>
      <label for="pw">비밀번호</label>
      <input type="password" name="pw" id="pw">
      <span id="msg_pw"></span>
    </div>
    <div>
      <label for="pw_check">비밀번호 확인</label>
      <input type="password" id="pw_check">
      <span id="msg_pw_check"></span>
    </div>
    <div>
      <label for="name">이름</label>
      <input type="text" id="name" name="name">
      <span id="msg_name"></span>
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
      <label for="mobile">휴대전화(- 제외)</label>
      <input type="text" name="mobile" id="mobile">
      <span id="msg_mobile"></span>
    </div>
  
    <div>
      <input type="hidden" name="event" value="${event}">
      <button>회원가입</button>
    </div>
    
  </form>

</div>

<%@ include file="../layout/footer.jsp" %>
