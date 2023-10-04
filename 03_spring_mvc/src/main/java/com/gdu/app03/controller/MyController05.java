package com.gdu.app03.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MyController05 {
   
  //@RequestMapping(value="/faq/add.do", method=RequestMethod.POST)
  public String add(HttpServletRequest request) {
    String title = request.getParameter("title");
    String content = request.getParameter("content");
    
    int addResult = title.isEmpty() ? 0 : 1;
    
    return "redirect:/faq/list.do?addResult=" + addResult;
  }
  
  //@RequestMapping(value="/faq/list.do", method=RequestMethod.GET)
  public String list(@RequestParam(value="addResult", required=false) String addResult, Model model){
    
    model.addAttribute("addResult", addResult);
    
    return "faq/list";
    
  }
  
  @RequestMapping(value="/faq/add.do", method=RequestMethod.POST)
  public String add2(HttpServletRequest request, 
                     RedirectAttributes redirectAttributes) {
    
    String title = request.getParameter("title");
    String content = request.getParameter("content");
    
    int addResult = title.isEmpty() ? 0 : 1;
    
    redirectAttributes.addFlashAttribute("addResult", addResult);
    
    return "redirect:/faq/list.do";
   
  }
  
  @RequestMapping(value="/faq/list.do", method=RequestMethod.GET)
  public String list2(){
    return "faq/list";
    
  }
  
}
