package com.gdu.staff.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.staff.dto.StaffDto;
import com.gdu.staff.service.StaffService;

import lombok.RequiredArgsConstructor;

@RequestMapping(value="/staff")
@RequiredArgsConstructor
@Controller
public class StaffController {
  
  private final StaffService staffService;
  
  @GetMapping(value="/list.json", produces="application/json")
  public ResponseEntity<List<StaffDto>> list() {
    return staffService.getStaffList();
  }
  
  @GetMapping(value="/query.json", produces="application/json")
  public ResponseEntity<StaffDto> query(@RequestParam("query") String sno) {
    return staffService.getStaff(sno);
  }
  
  @PostMapping(value="/add.do", produces="application/json")
  public ResponseEntity<Map<String, Object>> add(StaffDto staff) {
    return staffService.registerStaff(staff);
  }
  
}
