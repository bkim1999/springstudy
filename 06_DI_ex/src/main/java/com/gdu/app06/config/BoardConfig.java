package com.gdu.app06.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gdu.app06.dto.BoardDto;

@Configuration
public class BoardConfig {
  
  @Bean
  public BoardDto boardDto1() {
    return new BoardDto(1, "김민지1", "김범철", "갓갓민지.jpg");
  }
  
  @Bean
  public BoardDto boardDto2() {
    return new BoardDto(2, "김민지2", "김범철", "갓민지.jpeg");
  }
  
  @Bean
  public BoardDto boardDto3() {
    return new BoardDto(3, "김민지3", "김범철", "그저갓민지.jpg");
  }
  
}
