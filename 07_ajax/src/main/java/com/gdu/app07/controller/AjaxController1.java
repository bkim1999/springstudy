package com.gdu.app07.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.app07.dto.AjaxDto;
import com.gdu.app07.service.AjaxService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping(value="/ajax1")
public class AjaxController1 {

  private final AjaxService ajaxService;
  
  @ResponseBody
  @RequestMapping(value="/list.do", method=RequestMethod.GET, produces="application/json; charset=UTF-8")
  public List<AjaxDto> list(){
    return ajaxService.getDtoList();
  }
  
  @ResponseBody
  @RequestMapping(value="/detail.do", method=RequestMethod.GET, produces="application/json; charset=UTF-8")
  public AjaxDto detail(String name) {
    return ajaxService.getDto(name);
  }
}
