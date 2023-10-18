package com.gdu.app14.controller;

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

@RequiredArgsConstructor
@RestController
public class MemberController {

  private final MemberService memberService;
  
  @RequestMapping(value="/members", method=RequestMethod.POST, produces="application/json")
  public Map<String, Object> registerMember(@RequestBody MemberDto memberDto, HttpServletResponse response){
    return memberService.register(memberDto, response);
  }
  
  @RequestMapping(value="/members/page/{p}", method=RequestMethod.GET, produces="application/json")
  public Map<String, Object> getMembers(@PathVariable(value="p", required=false) Optional<String> opt, HttpServletResponse response){
    int page = Integer.parseInt(opt.orElse("1"));
    return memberService.getMembers(page);
  }
  
  @RequestMapping(value="/member/{no}", method=RequestMethod.GET, produces="application/json")
  public Map<String, Object> getMember(@PathVariable(value="no", required=false) Optional<String> opt, HttpServletResponse response){
    int memberNo = Integer.parseInt(opt.orElse("0"));
    return memberService.getMember(memberNo);
  }
  
  @RequestMapping(value="/member/", method=RequestMethod.PUT, produces="application/json")
  public Map<String, Object> modifyMember(@RequestBody MemberDto memberDto, HttpServletResponse response){
    return memberService.modifyMember(memberDto);
  }
  
  @RequestMapping(value="/member/{no}", method=RequestMethod.DELETE, produces="application/json")
  public Map<String, Object> deleteMember(@PathVariable(value="no", required=false) int memberNo, HttpServletResponse response){
    return memberService.deleteMember(memberNo);
  }
  
  @RequestMapping(value="/members/{memberNoList}", method=RequestMethod.DELETE, produces="application/json")
  public Map<String, Object> deleteMembers(@PathVariable(value="memberNoList", required=false) String memberNoList, HttpServletResponse response){
    return memberService.deleteMembers(memberNoList);
  }
  
}
