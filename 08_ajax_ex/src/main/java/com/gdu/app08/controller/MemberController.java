package com.gdu.app08.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.app08.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping(value="/member")
@Controller
public class MemberController {
  
  private final MemberService memberService;
  
  @ResponseBody
  @GetMapping(value="/health.check", produces="application/json; charset=UTF-8")
  public Map<String, Object> bmiInfo(int memberNo){
    return memberService.getBmiInfo(memberNo);
  }
  
  @ResponseBody
  @GetMapping(value="/profile.display", produces="application/octet-stream")
  public byte[] profile(int memberNo) {
    return memberService.getProfileImage(memberNo);
  }
  
}
