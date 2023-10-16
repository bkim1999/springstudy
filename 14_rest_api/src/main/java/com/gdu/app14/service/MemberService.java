package com.gdu.app14.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.gdu.app14.dto.MemberDto;

public interface MemberService {
  public Map<String, Object> register(MemberDto memberDto, HttpServletResponse response);
  public Map<String, Object> getMembers(int page);
}
