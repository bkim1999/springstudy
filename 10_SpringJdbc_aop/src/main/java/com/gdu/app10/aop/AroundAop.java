package com.gdu.app10.aop;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Aspect
public class AroundAop {

  @Pointcut("execution(* com.gdu.app10.controller.*Controller.*(..))")
  public void setPointCut() {}
  
  @Around("setPointCut()")
  public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    log.info("===============================================");
    Object obj = proceedingJoinPoint.proceed();
    log.info("{}", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date()));
    return obj;
  }
  
}
