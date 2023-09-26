package com.gdu.app02.anno01;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Fitness {
  private List<Member> members;
  
  public void info() {
    for(Member member : members) {
      System.out.println(member);
    }
  }
}
