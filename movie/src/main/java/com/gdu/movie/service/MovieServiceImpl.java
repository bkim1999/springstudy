package com.gdu.movie.service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.gdu.movie.dao.MovieMapper;
import com.gdu.movie.dto.MovieDto;
import com.gdu.movie.util.MySecurityUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {
  
  private final MovieMapper movieMapper;
  private final MySecurityUtils mySecurityUtils;
  
  @Override
  public Map<String, Object> getMovieList() {
    int movieCount = movieMapper.getMovieCount();
    List<MovieDto> list = movieMapper.getMovieList();
    return Map.of("message", "전체 " + movieCount + "개의 목록을 가져왔습니다."
                , "list", list
                , "status", 200);
  }
  
  @Override
  public Map<String, Object> getMovieSearchResult(HttpServletRequest request) {
    
    String column = request.getParameter("column");
    String searchText = mySecurityUtils.preventXSS(request.getParameter("searchText"));
    Map<String, Object> map = Map.of("column", column
                                   , "searchText", searchText);
    
    int movieCount = movieMapper.getMovieSearchResultCount(map);
    
    if(movieCount == 0) {
      Map<String, Object> resMap = new HashMap<>();
      resMap.put("message", searchText + " 검색 결과가 없습니다.");
      resMap.put("list", null);
      resMap.put("status", 500);
      return resMap;
    }
    
    List<MovieDto> list = movieMapper.getMovieSearchResult(map);
    
    return Map.of("message", movieCount + "개의 검색 결과가 있습니다."
                , "list", list
                , "status", 200);
    
  }
  
}
