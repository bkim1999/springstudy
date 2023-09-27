package com.gdu.app03.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.app03.dto.BlogDto;

@Controller
public class MyController03 {
  
  //@RequestMapping("/blog/detail.do")
  public String blogDetail(HttpServletRequest request, Model model) {
    String blogNo = request.getParameter("blogNo");
    model.addAttribute("blogNo", blogNo);
    return "blog/detail";
  }
  
  //@RequestMapping("/blog/detail.do")
  public String blogDetail2(@RequestParam(value="blogNo", required=false) int blogNo, Model model ) {
    model.addAttribute("blogNo", blogNo);
    return "blog/detail";
  }
  
  //@RequestMapping("/blog/detail.do")
  public String blogDetail3(BlogDto dto) {
    return null;
  }
  
  @RequestMapping("/blog/detail.do")
  public String blogDetail4(@ModelAttribute("dto") BlogDto dddto) {
    return null;
  }
  
}
