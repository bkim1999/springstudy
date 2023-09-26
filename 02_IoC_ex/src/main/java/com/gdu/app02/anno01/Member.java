package com.gdu.app02.anno01;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Member {
  private String name;
  private double height;
  private double weight;
  private double bmi;
  private String status;
  private Calculator calculator;
}
