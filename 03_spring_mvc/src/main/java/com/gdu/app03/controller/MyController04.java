package com.gdu.app03.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@SessionAttributes("title")
@Controller
public class MyController04 {
  
  @RequestMapping("/article/add.do")
  public String add1(HttpServletRequest request) {
    
    HttpSession session = request.getSession();
    session.setAttribute("title", request.getParameter("title"));
    return "article/result";

  }
  
  //@GetMapping("/article/add.do")
  public String add2(HttpSession session, HttpServletRequest request) {
    session.setAttribute("title", request.getParameter("title"));
    return "article/result";
  }
  
  //@GetMapping("/article/main.do")
  public String main(HttpSession session) {
    session.invalidate();
    return "index";
  }
  
  //@GetMapping("/article/confirm.do")
  public String confirm(HttpSession session) {
    String title = (String)session.getAttribute("title");
    System.out.println(title);
    return "index";
  }
  
  @GetMapping("/article/confirm.do")
  public String confirm2(@SessionAttribute("title") String title) {
    System.out.println(title);
    return "index";
  }
  
  @GetMapping("/article/main.do")
  public String main2(SessionStatus sessionStatus) {
    
    sessionStatus.setComplete();
    return "index";
    
  }
  
}
