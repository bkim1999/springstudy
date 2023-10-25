package com.gdu.myhome.intercept;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.gdu.myhome.dto.InactiveUserDto;

@Component
public class InactiveUserLoginInterceptor implements HandlerInterceptor {
  
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    
    InactiveUserDto inactiveUser = request.
    if(inactiveUser != null) {
      request.getSession().setAttribute("inactiveUser", inactiveUser);
      response.sendRedirect(request.getContextPath() + "/user/active.form");
    }
  }
  
}
