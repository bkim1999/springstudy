package com.gdu.app07.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gdu.app07.dto.AjaxDto;
import com.gdu.app07.service.AjaxService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/ajax2")
public class AjaxController2 {

  private final AjaxService ajaxService;
  
  @RequestMapping(value="/list.do", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
  public List<AjaxDto> getDtolist(){
    return ajaxService.getDtoList();
  }
  
  @RequestMapping(value="/detail.do", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
  public AjaxDto detail(String name) {
    return ajaxService.getDto(name);
  }
}
