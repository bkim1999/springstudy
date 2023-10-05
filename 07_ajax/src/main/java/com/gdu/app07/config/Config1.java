package com.gdu.app07.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gdu.app07.dto.AjaxDto;

@Configuration
public class Config1 {
  
  @Bean
  public AjaxDto a() {
    return new AjaxDto("김민지", 20);
  }
  
  @Bean
  public AjaxDto b() {
    return new AjaxDto("강해린", 18);
  }
  
  @Bean
  public AjaxDto c() {
    return new AjaxDto("팜하니", 20);
  }
  
}
