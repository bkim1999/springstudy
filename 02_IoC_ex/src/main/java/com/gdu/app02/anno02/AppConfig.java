package com.gdu.app02.anno02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
  
  @Bean
  public MyJdbcConnection myCon() {
    MyJdbcConnection myCon = new MyJdbcConnection();
    myCon.setDriver("oracle.jdbc.OracleDriver");
    myCon.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:xe");
    myCon.setUser("GD");
    myCon.setPassword("1111");
    return myCon;
  }
  
  @Bean
  public MyJdbcDao dao() {
    return new MyJdbcDao();
  }
  
  @Bean
  
  public MyJdbcService service() {
    return new MyJdbcService(dao());
  }
  
}
