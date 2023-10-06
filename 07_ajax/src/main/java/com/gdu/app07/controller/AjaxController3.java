package com.gdu.app07.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdu.app07.dto.AjaxDto;
import com.gdu.app07.service.AjaxService;

import lombok.RequiredArgsConstructor;

@RequestMapping(value="/ajax3")
@RequiredArgsConstructor
@Controller
public class AjaxController3 {

  private final AjaxService ajaxService;

  @GetMapping(value="/list.do", produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<AjaxDto>> getDtoList(){
    return new ResponseEntity(ajaxService.getDtoList(), HttpStatus.OK);
  }
  
  @PostMapping(value="/detail.do")
  public ResponseEntity<AjaxDto> detail(@RequestBody AjaxDto ajaxDto) {
    HttpHeaders header = new HttpHeaders();
    header.add("Content-Type", "application/json; charset=UTF-8");
    return new ResponseEntity<AjaxDto>(ajaxService.getDto(ajaxDto.getName()), header, HttpStatus.OK);
  }
}
