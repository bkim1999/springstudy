package com.gdu.app07.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gdu.app07.dao.AjaxDao;
import com.gdu.app07.dto.AjaxDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ServiceImpl1 implements AjaxService{

  private final AjaxDao ajaxDao;
  
  @Override
  public List<AjaxDto> getDtoList() {
    return ajaxDao.getDtoList();
  }
  
  @Override
  public AjaxDto getDto(String name) {
    int no = 0;
    if(name.equals("김민지")){
      no = 1;
    } else if(name.equals("강해린")) {
      no = 2;
    } else if(name.equals("팜하니")){
      no = 3;
    }
    return ajaxDao.getDto(no);
  }
  
}
