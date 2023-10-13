package com.gdu.app13.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class MyFileUtil {
  
  public String getPath() {
    LocalDate today = LocalDate.now();
    return "/storage/" + today.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
  }
  
  public String getFilesystemName(String originalName) {
    
    String extName = null;
    if(originalName.endsWith(".tar.gz")) {
      extName = ".tar.gz";
    } else {
      extName = originalName.substring(originalName.lastIndexOf("."));
    }
    return UUID.randomUUID().toString().replace("-", "") + extName;
    
  }
  
}
