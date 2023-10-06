package com.gdu.app08.service;

import java.util.List;
import java.util.Map;

import com.gdu.app08.dto.SearchDto;

public interface ShopService {
  public String getProductList(SearchDto searchDto);
}
