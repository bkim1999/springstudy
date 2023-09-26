package com.gdu.app02.anno02;

import java.sql.Connection;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class MyJdbcDao {
  
  private Connection con;
  
  private MyJdbcConnection myCon;
  
  public Connection getConnection() {
    AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
    myCon = ctx.getBean("myCon", MyJdbcConnection.class);
    ctx.close();
    return myCon.getConnection();
  }
  
  private void close() {
    try {
      if(con != null)
        con.close();
        System.out.println(myCon.getUser() + " 접속 해제");
    } catch(Exception e) {
        e.printStackTrace();
    }
  }
  
  public void add() {
   
    con = getConnection();
    System.out.println("add() 호출");
    close();
    
  }
  
  public void remove() {

    con = getConnection();
    System.out.println("remove() 호출");
    close();
    
  }
  
  public void modify() {

    con = getConnection();
    System.out.println("modify() 호출");
    close();
    
  }
  
  public void select() {

    con = getConnection();
    System.out.println("select() 호출");
    close();
    
  }
  
}
