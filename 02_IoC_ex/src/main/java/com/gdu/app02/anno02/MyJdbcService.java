package com.gdu.app02.anno02;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MyJdbcService {
  private MyJdbcDao dao;
  
  public void add() {
    dao.add();
  }
  
  public void remove() {
    dao.remove();
  }
  
  public void modify() {
    dao.modify();
  }
  
  public void select() {
    dao.select();
  }
  
}
