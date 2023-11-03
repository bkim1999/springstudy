package com.gdu.movie.util;

import org.springframework.stereotype.Component;

@Component
public class MySecurityUtils {
  
  public String preventXSS(String source) {
    return source.replace("<", "&lt").replace(">", "&gt");
  }
  
}
