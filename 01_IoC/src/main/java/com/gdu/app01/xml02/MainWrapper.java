package com.gdu.app01.xml02;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainWrapper {

  public static void main(String[] args) {

    AbstractApplicationContext ctx = new GenericXmlApplicationContext("xml02/app-context.xml");

    User user = ctx.getBean("user", User.class);
    System.out.println(user);

    Board board = ctx.getBean("board", Board.class);
    System.out.println(board);
    
    ctx.close();
    
  }

}
