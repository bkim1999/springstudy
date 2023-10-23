/**
 * 가입 이전 단계: 약관 동의 페이지
 */

const getContextPath = () => {
  var hostIndex = location.href.indexOf(location.host) + location.host.length;
  var contextPath = location.href.substring(hostIndex, location.href.indexOf('/', hostIndex + 1));
  return contextPath;
} 

var pwPassed = false;
var pwPassedCheck = false;

$(() => {
  fnCheckPassword();
  fnCheckPasswordCheck();
  fnChangePassword();
});

const fnCheckPassword = () => {
  $('#pw').keyup((ev) => {
    let pw = ev.target.value;
    let validPwCount = (/[A-Z]/.test(pw)
                    +  /[a-z]/.test(pw)
                    +  /[0-9]/.test(pw)
                    +  /[^A-Za-z0-9]/.test(pw)) >= 2;
    let validPwLength = pw.length >= 8 && pw.length <= 20;
    pwPassed = validPwCount && validPwLength;
    if(pwPassed){
      $('#msg_pw').text('사용 가능한 비밀번호입니다.');       
    } else{        
      $('#msg_pw').text('비밀번호는 8~20자이며 영문 대문자/소문자/숫자/특수문자 중 2가지 이상을 포함해야 합니다.');       
    }
  });
}

const fnCheckPasswordCheck = () => {
  $('#pw_check').keyup((ev) => {
    let pw = $('#pw').val();
    let pw_check = ev.target.value;
    pwPassedCheck = pw === pw_check;
    if(pwPassedCheck){
      $('#msg_pw_check').text('비밀번호와 비밀번호 확인이 일치합니다.');       
      pwPassed = true;
    } else{
      $('#msg_pw_check').text('비밀번호와 비밀번호 확인이 일치하지 않습니다.');       
      pwPassed = false;
    }
  });
}

const fnChangePassword = () => {
  $('#frm_changePw').submit((ev) => {
    if(!(pwPassed && pwPassedCheck)){
      alert('비밀번호를 확인하십시오.');
      ev.preventDefault();
    }
  });
}