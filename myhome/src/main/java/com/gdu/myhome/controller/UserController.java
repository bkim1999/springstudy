package com.gdu.myhome.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.myhome.dto.UserDto;
import com.gdu.myhome.service.UserService;

import lombok.RequiredArgsConstructor;

@RequestMapping(value="/user")
@RequiredArgsConstructor
@Controller
public class UserController {
  
  private final UserService userService;
  
  @GetMapping(value="/login.form")
  public String loginForm(HttpServletRequest request, Model model) throws Exception {
    String referer = request.getHeader("referer");
    model.addAttribute("referer", referer == null ? request.getContextPath() + "/main.do" : referer);
    model.addAttribute("url", userService.getNaverLoginURL(request));
    return "user/login";
  }
  
  @PostMapping(value="/login.do")
  public void login(HttpServletRequest request, HttpServletResponse response) throws Exception{
    userService.login(request, response);
  }
  
  @GetMapping(value="/naver/getAccessToken.do")
  public String getAccessToken(HttpServletRequest request) throws Exception{
    String accessToken = userService.getNaverAccessToken(request);
    return "redirect:/user/naver/getProfile.do?accessToken=" + accessToken;
  }
  
  @GetMapping(value="/naver/getProfile.do")
  public String getProfile(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    UserDto naverProfile = userService.getNaverProfile(request.getParameter("accessToken"));
    UserDto user = userService.getUser(naverProfile.getEmail());
    if(user == null) {
      model.addAttribute("naverProfile", naverProfile);
      return "user/naver_join";
    } else {
      userService.naverLogin(request, response, naverProfile);
      return "redirect:/main.do";
    }
  }
  
  @PostMapping(value="/naver/join.do")
  public void naverJoin(HttpServletRequest request, HttpServletResponse response) throws Exception {
    userService.insertNaverUser(request, response);
  }
  
  @GetMapping(value="/logout.do")
  public void logout(HttpServletRequest request, HttpServletResponse response) {
    userService.logout(request, response);
  }
  
  @GetMapping(value="/agree.form")
  public String agreeForm(HttpServletRequest request, HttpServletResponse response) {
    return "user/agree";
  }
  
  @GetMapping(value="/join.form")
  public String joinForm(@RequestParam(value="service", required=false, defaultValue="off") String service,
                         @RequestParam(value="event", required=false, defaultValue="off") String event,
                         Model model) {
    String rv = null;
    if(service.equals("off")) {
      rv = "redirect:/main.do";
    } else {
      model.addAttribute("event", event); // event: "on" or "off"
      rv = "user/join";
    }
    
    return rv;
    
  }
  
  @GetMapping(value="/checkEmail.do")
  public ResponseEntity<Map<String, Object>> checkEmail(@RequestParam String email){
    return userService.checkEmail(email);
  }
  
  @GetMapping(value="/sendCode.do", produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String, Object>> sendCode(@RequestParam String email){
    System.out.println("sending code...");
    return userService.sendCode(email);
  }
  
  @PostMapping(value="/join.do")
  public void join(HttpServletRequest request, HttpServletResponse response) {
    userService.join(request, response);
  }
  
  @GetMapping(value="/mypage.form")
  public String mypage(HttpServletRequest request) {
    return "user/mypage";
  }
  
  @PostMapping(value="/modify.do", produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String, Object>> modify(HttpServletRequest request, HttpServletResponse response) {
    return userService.modify(request, response);
  }
  
  @GetMapping(value="/changePw.form")
  public String changePwForm() {
    return "user/changePw";
  }
  
  @PostMapping(value="/changePw.do")
  public void changePw(HttpServletRequest request, HttpServletResponse response) {
    userService.changePw(request, response);
  }
  
  @PostMapping(value="/leave.do")
  public void leaveUser(HttpServletRequest request, HttpServletResponse response) {
    userService.leaveUser(request, response);
  }   
  
  @GetMapping(value="/active.form")
  public String activeForm(){
    return "user/active";
  }   
  
  @GetMapping(value="/active.do")
  public void active(HttpServletRequest request, HttpServletResponse response){
    userService.active(request.getSession(), request, response);
  }   
  
  
  
}
