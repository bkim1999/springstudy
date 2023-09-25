package com.gdu.app02.xml02;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainWrapper {

  public static void main(String[] args) {
    
    AbstractApplicationContext ctx = new GenericXmlApplicationContext("xml02/appCtx.xml");
    
    MyJdbcService service = ctx.getBean("service", MyJdbcService.class);
    
    service.add();
    service.remove();
    service.modify();
    service.select();
    
    ctx.close();
    
    return;
    
  }

}
