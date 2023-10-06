package com.gdu.app08.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.app08.dto.SearchDto;
import com.gdu.app08.service.ShopService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ShopController {
  
  private final ShopService shopService;
  
  @ResponseBody
  @GetMapping(value="/search.do", produces="application/json; charset=UTF-8")
  public String getProductList(SearchDto searchDto) {
    return shopService.getProductList(searchDto);
  }
  
}
