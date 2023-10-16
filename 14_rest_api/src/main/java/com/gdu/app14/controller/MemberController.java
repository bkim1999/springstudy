package com.gdu.app14.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gdu.app14.dto.MemberDto;
import com.gdu.app14.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping(value="/members")
@RequiredArgsConstructor
@RestController
public class MemberController {

  private final MemberService memberService;
  
  @RequestMapping(value="", method=RequestMethod.POST, produces="application/json")
  public Map<String, Object> registerMember(@RequestBody MemberDto memberDto, HttpServletResponse response){
    return memberService.register(memberDto, response);
  }
  
  @RequestMapping(value="/page/{p}", method=RequestMethod.GET, produces="application/json")
  public Map<String, Object> getMembers(@PathVariable(value="p", required=false) Optional<String> opt, HttpServletResponse response){
    int page = Integer.parseInt(opt.orElse("1"));
    return memberService.getMembers(page);
  }
  
}
