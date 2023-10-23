/**
 * 가입 이전 단계: 약관 동의 페이지
 */

const getContextPath = () => {
  var hostIndex = location.href.indexOf(location.host) + location.host.length;
  var contextPath = location.href.substring(hostIndex, location.href.indexOf('/', hostIndex + 1));
  return contextPath;
} 

$(() => {
  fnModifyUser();
  fnChangePwForm();
  fnLeaveUser();
});

const fnModifyUser = () => {
  $('#btn_modify').click(() => {
    $.ajax({
      type: 'post',
      url: getContextPath() + '/user/modify.do',
      data: $('#frm_mypage').serialize(),
      dataType: 'json',
      success: (resData) => {
        if(resData.modifyResult === 1){
          alert('회원 정보가 수정되었습니다.');
        } else {
          alert('회원 정보가 수정되지 않았습니다.');
        }
      }
    });
  });
}

const fnChangePwForm = () => {
  $('#btn_changePw').click((ev) => {
    location.href = getContextPath() + '/user/changePw.form';
  });
}

const fnLeaveUser = () => {
  $('#btn_leaveUser').click((ev) => {
    location.href = getContextPath() + '/user/leaveUser.do';
  });
}