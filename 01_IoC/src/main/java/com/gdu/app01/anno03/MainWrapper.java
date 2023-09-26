package com.gdu.app01.anno03;

import java.util.Map.Entry;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class MainWrapper {

  public static void main(String[] args) {

    AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
    
    Student s = ctx.getBean("student", Student.class);
    
    for(String subject : s.getSubjects()) {
      System.out.println(subject);
    }
   
    for(String contact : s.getContacts()) {
      System.out.println(contact);
    }
    for(Entry<String, String> entry: s.getFriends().entrySet()) {
      System.out.println(entry);
    }
    
    ctx.close();
  
  }

}
