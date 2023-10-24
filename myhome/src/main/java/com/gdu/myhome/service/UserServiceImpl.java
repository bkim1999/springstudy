package com.gdu.myhome.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.myhome.dao.UserMapper;
import com.gdu.myhome.dto.InactiveUserDto;
import com.gdu.myhome.dto.UserDto;
import com.gdu.myhome.util.MyJavaMailUtils;
import com.gdu.myhome.util.MySecurityUtils;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
  
  private final UserMapper userMapper;
  private final MySecurityUtils mySecurityUtils;
  private final MyJavaMailUtils myJavaMailUtils;
  
  private final String client_id = "Xk1dzDD4xWwGJRSkQWGL";
  private final String client_secret = "mdqqoeOWZH";
  
  @Override
  public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {

    String email = request.getParameter("email");
    String pw = mySecurityUtils.getSHA256(request.getParameter("pw"));
    
    Map<String, Object> map = Map.of("email", email, "pw", pw);
    
    InactiveUserDto inactiveUser = userMapper.getInactiveUser(map);
    

    HttpSession session = request.getSession();
    if(inactiveUser != null) {
      session.setAttribute("inactiveUser", inactiveUser);
      response.sendRedirect(request.getContextPath() + "/user/active.form");
    }
    
    UserDto user = userMapper.getUser(map);
    
    if(user != null) {
      session.setAttribute("user", user);
      userMapper.insertAccess(email);
      try{
        response.sendRedirect(request.getContextPath() + "/main.do");
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      try {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<script>");
        out.println("alert('존재하지 않는 사용자입니다.')");
        out.println("location.href='" + request.getContextPath() + "/main.do';");
        out.println("</script>");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  
  @Override
  public String getNaverLoginURL(HttpServletRequest request) throws Exception {
    String apiURL = "https://nid.naver.com/oauth2.0/authorize";
    String response_type = "code";
    String client_id = "Xk1dzDD4xWwGJRSkQWGL";
    String redirect_uri = URLEncoder.encode("http://localhost:8080" + request.getContextPath() + "/user/naver/getAccessToken.do", "UTF-8");
    String state = new BigInteger(130, new SecureRandom()).toString();
    
    StringBuilder sb = new StringBuilder();
    sb.append(apiURL);
    sb.append("?response_type=" + response_type);
    sb.append("&client_id=" + client_id);
    sb.append("&redirect_uri=" + redirect_uri);
    sb.append("&state=" + state);
    
    request.getSession().setAttribute("state", state);
    
    return sb.toString();
  }
  
  @Override
  public String getNaverAccessToken(HttpServletRequest request) throws Exception {

    String code = request.getParameter("code");
    String state = request.getParameter("state");
    request.getSession().setAttribute("state", state);
    
    String apiURL = "https://nid.naver.com/oauth2.0/token";
    String grant_type = "authorization_code";
    
    StringBuilder sb = new StringBuilder();
    sb.append(apiURL);
    sb.append("?grant_type=" + grant_type);
    sb.append("&client_id=" + client_id);
    sb.append("&client_secret=" + client_secret);
    sb.append("&code=" + code);
    sb.append("&state=" + state);
    
    URL url = new URL(sb.toString());
    HttpURLConnection con = (HttpURLConnection)url.openConnection();
    con.setRequestMethod("GET");
    
    BufferedReader reader = null;
    int responseCode = con.getResponseCode();
    if(responseCode == 200) {
      reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
    } else {
      reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
    }
    
    String line = null;
    StringBuilder responseBody = new StringBuilder();
    while((line = reader.readLine()) != null) {
      responseBody.append(line);
    }
    
    JSONObject obj = new JSONObject(responseBody.toString());
    return obj.getString("access_token");  
    
  }
  
  @Override
  public UserDto getNaverProfile(String accessToken) throws Exception {

    String apiURL = "https://openapi.naver.com/v1/nid/me";
    URL url = new URL(apiURL);
    HttpURLConnection con = (HttpURLConnection)url.openConnection();
    con.setRequestMethod("GET");
    con.setRequestProperty("Authorization", "Bearer " + accessToken);
    
    BufferedReader reader = null;
    int responseCode = con.getResponseCode();
    if(responseCode == 200) {
      reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
    } else {
      reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
    }
    
    String line = null;
    StringBuilder responseBody = new StringBuilder();
    while((line = reader.readLine()) != null) {
      responseBody.append(line);
    }
    
    JSONObject obj = new JSONObject(responseBody.toString());
    JSONObject response = obj.getJSONObject("response");
    UserDto user = UserDto.builder()
                    .email(response.getString("email"))
                    .name(response.getString("name"))
                    .gender(response.getString("gender"))
                    .mobile(response.getString("mobile"))
                    .build();
    
    return user;
    
  }
  
  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response) {

    HttpSession session = request.getSession();
    session.invalidate();
    
    try{
      response.sendRedirect(request.getContextPath() + "/main.do");
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }
  
  @Transactional(readOnly=true)
  @Override
  public ResponseEntity<Map<String, Object>> checkEmail(String email) {
    
    Map<String, Object> map = Map.of("email", email);
    boolean enable = userMapper.getUser(map) == null 
                  && userMapper.getLeaveUser(map) == null 
                  && userMapper.getInactiveUser(map) == null;
    return new ResponseEntity<>(Map.of("enable", enable), HttpStatus.OK);
    
  }
  
  @Override
  public ResponseEntity<Map<String, Object>> sendCode(String email) {
    String code = mySecurityUtils.getRandomString(6, true, true);
    myJavaMailUtils.sendJavaMail(email, "myhome 인증 코드", 
        "<div>인증코드는 <strong>" + code + "</strong>입니다.</div>");
    return new ResponseEntity<>(Map.of("code", code), HttpStatus.OK);
  }
  
  @Override
  public void join(HttpServletRequest request, HttpServletResponse response) {

    String email = request.getParameter("email");
    String pw = mySecurityUtils.getSHA256(request.getParameter("pw"));
    String name = mySecurityUtils.preventXSS(request.getParameter("name"));
    String gender = request.getParameter("gender");
    String mobile = request.getParameter("mobile");
    String postcode = request.getParameter("postcode");
    String roadAddress = request.getParameter("roadAddress");
    String jibunAddress = request.getParameter("jibunAddress");
    String detailAddress = mySecurityUtils.preventXSS(request.getParameter("detailAddress"));
    String event = request.getParameter("event");
    
    UserDto user = UserDto.builder()
                    .email(email)
                    .pw(pw)
                    .name(name)
                    .gender(gender)
                    .mobile(mobile)
                    .postcode(postcode)
                    .roadAddress(roadAddress)
                    .jibunAddress(jibunAddress)
                    .detailAddress(detailAddress)
                    .agree(event.equals("on") ? 1 : 0)
                    .build();
    
    int joinResult = userMapper.insertUser(user);
    
    try {
      
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<script>");
      if(joinResult == 1) {
        request.getSession().setAttribute("user", user);
        userMapper.insertAccess(email);
        out.println("alert('회원가입에 성공하였습니다.');");
        out.println("location.href='" + request.getContextPath() + "/main.do';");
      } else {
        out.println("alert('회원가입에 실패하였습니다.");
        out.println("location.href='" + request.getContextPath() + "/agree.form';");
      }
      out.println("</script>");
      out.flush();
      out.close();
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }
  
  @Override
  public ResponseEntity<Map<String, Object>> modify(HttpServletRequest request, HttpServletResponse response) {
    
    int userNo = Integer.parseInt(request.getParameter("userNo"));
    String name = mySecurityUtils.preventXSS(request.getParameter("name"));
    String gender = request.getParameter("gender");
    String mobile = request.getParameter("mobile");
    String postcode = request.getParameter("postcode");
    String roadAddress = request.getParameter("roadAddress");
    String jibunAddress = request.getParameter("jibunAddress");
    String detailAddress = mySecurityUtils.preventXSS(request.getParameter("detailAddress"));
    String event = request.getParameter("event");
    
    UserDto user = UserDto.builder()
                    .userNo(userNo)
                    .name(name)
                    .gender(gender)
                    .mobile(mobile)
                    .postcode(postcode)
                    .roadAddress(roadAddress)
                    .jibunAddress(jibunAddress)
                    .detailAddress(detailAddress)
                    .agree(event.equals("on") ? 1 : 0)
                    .build();
    
    int modifyResult = userMapper.updateUser(user);
    
    if(modifyResult == 1) {
      request.getSession().setAttribute("user", user);
    }
    
    return new ResponseEntity<Map<String,Object>>(Map.of("modifyResult", modifyResult), HttpStatus.OK);
    
  }
  
  @Override
  public void changePw(HttpServletRequest request, HttpServletResponse response) {

    int userNo = Integer.parseInt(request.getParameter("userNo"));
    String pw = mySecurityUtils.getSHA256(request.getParameter("pw"));
    
    UserDto user = UserDto.builder()
                    .userNo(userNo)
                    .pw(pw)
                    .build();
    
    int changePwResult = userMapper.updateUserPw(user);

    try {
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<script>");
      if(changePwResult == 1) {
        UserDto sessionUser = (UserDto)request.getSession().getAttribute("user");
        sessionUser.setPw(pw);
        out.println("alert('비밀번호를 변경하였습니다.');");
        out.println("location.href='" + request.getContextPath() + "/user/mypage.form'");
        
      } else {
        out.println("alert('비밀번호를 변경에 실패하였습니다..');");
        out.println("location.href='" + request.getContextPath() + "/main.do'");
      }
      out.println("</script>");
      out.flush();
      out.close();
    } catch(Exception e) {
      e.printStackTrace();
    }
    
  }
  
  @Transactional
  @Override
  public void leaveUser(HttpServletRequest request, HttpServletResponse response) {
    
    Optional<String> opt = Optional.ofNullable(request.getParameter("userNo"));
    int userNo = Integer.parseInt(opt.orElse("0"));
    
    UserDto user = userMapper.getUser(Map.of("userNo", userNo));
    
    if(user == null) {
      try {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>");
        out.println("alert('회원 탈퇴를 수행할 수 없습니다.')");
        out.println("location.href='" + request.getContextPath() + "/main.do'");
        out.println("</script>");
        out.flush();
        out.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    
    int insertLeaveUserResult = userMapper.insertLeaveUser(user);
    int deleteUserResult = userMapper.deleteUser(user);
    
     try {
        
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<script>");
      if(insertLeaveUserResult == 1 && deleteUserResult == 1) {
        HttpSession session = request.getSession();
        session.invalidate();
        out.println("alert('회원 탈퇴되었습니다. 그 동안 이용해 주셔서 감사합니다.')");
        out.println("location.href='" + request.getContextPath() + "/main.do'");
      } else {
        out.println("alert('회원 탈퇴되지 않았습니다.')");
        out.println("history.back()");
      }
      out.println("</script>");
      out.flush();
      out.close();
    
    } catch (Exception e){
      e.printStackTrace();
    }
  }
  
  @Override
  public void inactiveUserBatch() {
    userMapper.insertInactiveUser();
    userMapper.deleteUserForInactive();
  }
  
  @Override
  public void active(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
    
    InactiveUserDto inactiveUser = (InactiveUserDto)session.getAttribute("inactiveUser");
    String email = inactiveUser.getEmail();
    
    int insertActiveUserResult = userMapper.insertActiveUser(email);
    int deleteInactiveUserResult = userMapper.deleteInactiveUser(email);
    
    try {
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<script>");
      if(insertActiveUserResult == 1 && deleteInactiveUserResult == 1) {
        out.println("alert('휴면 계정이 복구되었습니다.')");
        out.println("location.href='" + request.getContextPath() + "/main.do'");
      } else {
        out.println("alert('회원 탈퇴되지 않았습니다.')");
        out.println("history.back()");
      }
      out.println("</script>");
      out.flush();
      out.close();
    
    } catch (Exception e){
      e.printStackTrace();
    }
    
  }
  
}
