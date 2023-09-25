package com.gdu.app01.xml01;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainWrapper {

  public static void ex01() {
    
    AbstractApplicationContext ctx = new GenericXmlApplicationContext("xml01/app-context.xml");
    
    Calculator calculator = (Calculator)ctx.getBean("calc");
    
    calculator.add(1, 2);
    calculator.sub(1, 2);
    calculator.mul(2, 2);
    calculator.div(4, 2);
    calculator.add(1, 2);
    
    ctx.close();
    
  }
  
  public static void ex02() {
    
    AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("xml01/app-context.xml");
    
    Person man = (Person)ctx.getBean("man");
    Person woman = (Person)ctx.getBean("woman");
    
    System.out.println(man.getAge() + ", " + man.getName());
    man.getCalculator().add(1, 2);
    System.out.println(woman.getAge() + ", " + woman.getName());
    woman.getCalculator().add(3, 4);
    
    ctx.close();
    
  }
  
  public static void main(String[] args) {

    ex02();
    
  }

}
