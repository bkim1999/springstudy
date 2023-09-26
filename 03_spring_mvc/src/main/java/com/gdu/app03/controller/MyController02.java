package com.gdu.app03.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController02 {
  
  @GetMapping(value="/notice/list.do")
  public String noticeList() {
    return "notice/list";
  }
  
  @GetMapping(value="/member/list.do")
  public void memberList() {
    
  }
  
}
