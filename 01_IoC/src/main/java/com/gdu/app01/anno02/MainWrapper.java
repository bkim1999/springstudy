package com.gdu.app01.anno02;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class MainWrapper {

  public static void main(String[] args) {

    AbstractApplicationContext ctx = new AnnotationConfigApplicationContext("com.gdu.app01.anno02");

    User user = ctx.getBean("user", User.class);
    System.out.println(user);

    Board board = ctx.getBean("board", Board.class);
    System.out.println(board);
    
    ctx.close();
    
  }

}
