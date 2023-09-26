package com.gdu.app01.anno01;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
  
  @Bean
  public Calculator calc() {
    return new Calculator();
  }
  
  @Bean
  public Person man() {
    Person man = new Person();
    man.setName("김민지");
    man.setAge(20);
    man.setCalculator(calc());
    return man;
  }
  
  @Bean
  public Person woman() {
    return new Person("팜하니", 20, calc());
  }
  
}
