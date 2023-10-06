package com.gdu.app08.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gdu.app08.dto.MemberDto;

@Configuration
public class MemberConfig {
  
  @Bean
  public MemberDto member1() {
    return new MemberDto(1, "김민지", 170, 50); 
  }
  
  @Bean
  public MemberDto member2() {
    return new MemberDto(2, "팜하니", 160, 50); 
  }
  
  @Bean
  public MemberDto member3() {
    return new MemberDto(3, "강해린", 162, 50); 
  }
  
}
