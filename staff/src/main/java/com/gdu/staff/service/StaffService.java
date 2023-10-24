package com.gdu.staff.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.gdu.staff.dto.StaffDto;

public interface StaffService {
  public ResponseEntity<List<StaffDto>> getStaffList();
  public ResponseEntity<StaffDto> getStaff(String sno);
  public ResponseEntity<Map<String, Object>> registerStaff(StaffDto staff);
}
