package com.gdu.app01.anno02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
  
  @Bean(name="user")
  public User minji() {
    User user = new User();
    user.setUserId("김민지짱");
    user.setUserNo(2004);
    return user;
  }
  
  @Bean(name="board")
  public Board godminji() {
    return new Board("김민지가 최고인 100가지 이유", minji());
  }
}
