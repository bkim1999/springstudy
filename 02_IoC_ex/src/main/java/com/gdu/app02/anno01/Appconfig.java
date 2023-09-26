package com.gdu.app02.anno01;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Appconfig {
    
  @Bean
  public Calculator calc() {
    return new Calculator();
  }
  
  @Bean
  public Member member() {
    Member member = new Member();
    member.setName("김민지");
    member.setHeight(170);
    member.setWeight(50);
    member.setCalculator(calc());
    
    double h = member.getHeight();
    double w = member.getWeight();
    Calculator c = member.getCalculator();
    double bmi = c.div(w, c.div(c.mul(h, h), 10000));
    member.setBmi(bmi);
    if(member.getBmi() <= 20) {
      member.setStatus("저체중");
    } else if(member.getBmi() <= 25) {
        member.setStatus("정상");
    } else if(member.getBmi() <= 30) {
      member.setStatus("비만");
    } else {
      member.setStatus("정상");
    }

    return member;
  }
  
  @Bean
  public Fitness fitness() {
    return new Fitness(Arrays.asList(member()));
  }
  
}
