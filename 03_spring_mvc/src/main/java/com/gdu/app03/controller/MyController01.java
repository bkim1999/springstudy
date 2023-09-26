package com.gdu.app03.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MyController01 {
  
  /*
   * 요청 / 응답 처리하는 메소드
   * 
   * 1. 반환타입
   *  String: Jsp 이름
   *  void : 직접 응답
   *  기타 : 비동기 통신
   *  
   * 매개변수
   * HttpServletRequest
   * HttpServletResponse  
   * 
   * 
   */
  
  @RequestMapping(value={"/", "/index.do"}, method=RequestMethod.GET)
  public String welcome() {
    return "index";
  }
  
  @RequestMapping(value="/board/list.do", method=RequestMethod.GET)
  public String boardList() {
    return "board/list";
  }
  
}
