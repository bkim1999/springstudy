package com.gdu.myhome.intercept;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.gdu.myhome.dao.UserMapper;
import com.gdu.myhome.dto.InactiveUserDto;
import com.gdu.myhome.util.MySecurityUtils;

import lombok.RequiredArgsConstructor;

@Component
public class InactiveUserLoginInterceptor implements HandlerInterceptor {
  
  @Autowired
  private UserMapper userMapper;
  private MySecurityUtils mySecurityUtils;
  
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    
    String email = request.getParameter("email");
    String pw = mySecurityUtils.getSHA256(request.getParameter("pw"));
    Map<String, Object> map = Map.of("email", email, "pw", pw);
    System.out.println(email + pw);
    InactiveUserDto inactiveUser = userMapper.getInactiveUser(map);
    
    if(inactiveUser != null) {
      response.setContentType("text/html; charset=UTF-8");
      request.getSession().setAttribute("inactiveUser", inactiveUser);
      PrintWriter out = response.getWriter();
      out.println("<script>");
      out.println("if(confirm('휴면 계정입니다. 복구할까요?')){");
      out.println("location.href='" + request.getContextPath() + "/user/active.form'");
      out.println("} else {");
      out.println("history.back();");
      out.println("}");
      out.println("</script>");
      out.flush();
      out.close();
      return true;
    }
    return false;
    
  }
  
}
