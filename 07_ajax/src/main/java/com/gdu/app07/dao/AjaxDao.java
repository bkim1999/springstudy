package com.gdu.app07.dao;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gdu.app07.dto.AjaxDto;

@Repository
public class AjaxDao {
  
  private AjaxDto a;
  private AjaxDto b;
  private AjaxDto c;
  
  @Autowired
  public void setBeans(AjaxDto a, AjaxDto b, AjaxDto c) {
    this.a = a;
    this.b = b;
    this.c = c;
  }
  
  public List<AjaxDto> getDtoList(){
    return Arrays.asList(a, b, c);
  }
  
  public AjaxDto getDto(int no) {
    return (no == 1) ? a : (no == 2) ? b : (no == 3) ? c : new AjaxDto("이혜인", 16);
  }
  
}
