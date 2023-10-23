/**
 * 회원가입 페이지
 */

$(() => {
  fnCheckMail();
  fnCheckPassword();
  fnCheckPasswordCheck();
  fnCheckName();
  fnCheckMobile();
  fnJoin();
});

var emailPassed = false;
var pwPassed = false;
var pwPassedCheck = false;
var namePassed= false;
var mobilePassed = false;

const getContextPath = () => {
  var hostIndex = location.href.indexOf(location.host) + location.host.length;
  var contextPath = location.href.substring(hostIndex, location.href.indexOf('/', hostIndex + 1));
  return contextPath;
}

const fnCheckMail = () => {
  $('#btn_get_code').click(() => {
    let email = $('#email').val();
    var regEmail = /^[a-zA-Z]+([._-]{0,1}[a-zA-Z]+){2,}@[a-z]{2,}([.][a-z]{2,6}){1,2}$/;
    emailPassed = regEmail.test(email);
    new Promise((resolve, reject) => {
      if(!emailPassed){
        reject(1);
        return;
      }

      $.ajax({
        type: 'get',
        url: getContextPath() + '/user/checkEmail.do',
        data: 'email=' + email,
        dataType: 'json',
        success: (resData) => {
          if(resData.enable == true){
            resolve();
          } else{
            reject(2); 
          }
        }
      })
    }).then((state) => {
        $('#msg_email').text('사용할 수 있는 이메일입니다.')
        $.ajax({
          type: 'get',
          url: getContextPath() + '/user/sendCode.do',
          data: 'email=' + email,
          dataType: 'json',
          success: (resData) => {
            $('#btn_verify_code').click((ev) => {
              emailPassed = $('#code').val() === resData.code;
              if(emailPassed){
                alert('이메일 인증에 성공하였습니다.');
              } else{
                alert('이메일 인증에 실패하였습니다.');
              }
            });
          }
        });
    }).catch((state) => {
      emailPassed = false;
      switch(state){
      case 1: $('#msg_email').text('이메일 형식이 올바르지 않습니다.');
              break;
      case 2: $('#msg_email').text('이미 사용 중인 이메일입니다.');
      }
    })
  });
}

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

const fnCheckName = () => {
  $('#name').keyup((ev) => {
    let name = ev.target.value;
    namePassed = /^[ㄱ-ㅎ가-힣a-zA-Z]+$/.test(name);
    if(namePassed){
      $('#msg_name').text('');       
      namePassed = true;
    } else{
      $('#msg_name').text('잘못된 형식의 이름입니다.');       
      namePassed = false;
    }
  });
}

const fnCheckMobile = () => {
  $('#mobile').keyup((ev) => {
    ev.target.value = ev.target.value.replaceAll(/[^0-9]/g, '');
    let mobile = ev.target.value;
    mobilePassed = /[0-9]/.test(mobile) && (mobile.length === 11) && mobile.startsWith('010');
    if(mobilePassed){
      $('#msg_mobile').text('');
    } else{
      $('#msg_mobile').text('잘못된 전화번호입니다.');
    }
  });
}

const fnJoin = () => {
  $('#frm_join').submit((ev) => {
    if(!emailPassed){
      alert('이메일이 인증되지 않았습니다.');
      ev.preventDefault();
      return;
    } if(!pwPassed){
      alert('잘못된 비밀번호 형식입니다.');
      ev.preventDefault();
      return;
    } if(!pwPassedCheck){
      alert('비밀번호와 비밀번호 확인이 일치하지 않습니다.');
      ev.preventDefault();
      return;
    }
  })
}