package com.gdu.app07.controller;

import java.io.File;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdu.app07.service.AjaxService;

import lombok.RequiredArgsConstructor;

@RequestMapping(value="/ajax4")
@RequiredArgsConstructor
@Controller
public class AjaxController4 {

  private final AjaxService ajaxService;
  
  @GetMapping(value="/display.do", produces="application/octet-stream")
  public ResponseEntity<byte[]> display(String path, String filename) {
    
    ResponseEntity<byte[]> responseEntity = null;
    
    try {
     
      File file = new File(path, filename);
      
      byte[] b = FileCopyUtils.copyToByteArray(file);
      
      responseEntity = new ResponseEntity<byte[]>(b, HttpStatus.OK);
      
    } catch(Exception e) {
      e.printStackTrace();
    }
    return responseEntity;
  }
}
