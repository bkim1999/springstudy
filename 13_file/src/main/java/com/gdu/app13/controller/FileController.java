package com.gdu.app13.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.app13.service.FileService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class FileController {
  
  private final FileService fileService;
  
  @RequestMapping(value="/upload.do", method=RequestMethod.POST)
  public String upload(MultipartHttpServletRequest multiPartRequest, RedirectAttributes redirectAttributes) {
    int addResult = fileService.upload(multiPartRequest);
    redirectAttributes.addFlashAttribute("addResult", addResult);
    return "redirect:main.do";
  }
  
  @ResponseBody
  @RequestMapping(value="/ajax/upload.do", method=RequestMethod.POST, produces="application/json")
  public Map<String, Object> success(MultipartHttpServletRequest multipartRequest){
    return fileService.ajaxUpload(multipartRequest);
  }
  
}
