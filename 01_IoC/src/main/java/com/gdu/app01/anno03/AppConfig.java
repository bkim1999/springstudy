package com.gdu.app01.anno03;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
  
  @Bean
  public Student student() {
    Student student = new Student();
    student.setSubjects(Arrays.asList("민지", "해린", "하니"));
    student.setContacts(new HashSet<String>(Arrays.asList("010-0000-0000", "010-민지-하니")));
    student.setFriends(Map.of("민", "지", "하", "니"));
    return student;
  }
  
}
