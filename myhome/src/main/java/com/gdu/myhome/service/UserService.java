package com.gdu.myhome.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;

import com.gdu.myhome.dto.UserDto;

public interface UserService {
  public void login(HttpServletRequest request, HttpServletResponse response) throws Exception;
  public String getNaverLoginURL(HttpServletRequest request) throws Exception;
  public String getNaverAccessToken(HttpServletRequest request) throws Exception;
  public UserDto getNaverProfile(String accessToken) throws Exception;
  public void logout(HttpServletRequest request, HttpServletResponse response);
  public ResponseEntity<Map<String, Object>> checkEmail(String email);
  public ResponseEntity<Map<String, Object>> sendCode(String email);
  public void join(HttpServletRequest request, HttpServletResponse response);
  public ResponseEntity<Map<String, Object>> modify(HttpServletRequest request, HttpServletResponse response);
  public void changePw(HttpServletRequest request, HttpServletResponse response);
  public void leaveUser(HttpServletRequest request, HttpServletResponse response);
  public void inactiveUserBatch();
  public void active(HttpSession session, HttpServletRequest request, HttpServletResponse response);
}