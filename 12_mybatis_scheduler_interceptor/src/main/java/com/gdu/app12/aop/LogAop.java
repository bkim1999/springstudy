package com.gdu.app12.aop;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LogAop {
  
  @Pointcut("execution(* com.gdu.app12.controller.*Controller.*(..))")
  public void setPointCut() { }
  
  @Before("setPointCut()")
  public void beforeAdvice(JoinPoint joinPoint) {
    // 1. HttpServletRequest
    ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = servletRequestAttributes.getRequest();
    
    // 2. 요청 파라미터 -> Map 변환
    Map<String, String[]> map = request.getParameterMap();
    
    // 3. 요청 파라미터 출력 형태 만들기
    String params = "";
    if(map.isEmpty()) {
      params += "No Parameter";
    } else {
      for(Map.Entry<String, String[]> entry : map.entrySet()) {
        params += entry.getKey() + ":" + Arrays.toString(entry.getValue()) + " ";
      }
    }
    
    // 4. 로그 찍기 (치환 문자 {} 활용)
    log.info("{} {}", request.getMethod(), request.getRequestURI());  // 요청 방식, 요청 주소
    log.info("{}", params);   
  }
  
}
