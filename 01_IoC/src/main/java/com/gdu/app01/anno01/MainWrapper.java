package com.gdu.app01.anno01;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class MainWrapper {

  public static void ex01() {
    
    AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
    
    Calculator calculator = (Calculator)ctx.getBean("calc");
    
    calculator.add(1, 2);
    calculator.sub(1, 2);
    calculator.mul(2, 2);
    calculator.div(4, 2);
    
    
    ctx.close();
    
  }
  
  public static void ex02() {

    AbstractApplicationContext ctx = new AnnotationConfigApplicationContext("com.gdu.app01.anno01");
    
    Person man = (Person)ctx.getBean("man");
    Person woman = (Person)ctx.getBean("woman");
    
    System.out.println(man.getAge() + "살 " + man.getName());
    man.getCalculator().add(1, 2);
    System.out.println(woman.getAge() + "살 " + woman.getName());
    woman.getCalculator().add(3, 4);
    
    ctx.close();
    
  }
  
  public static void main(String[] args) {

    ex02();
    
  }

}
