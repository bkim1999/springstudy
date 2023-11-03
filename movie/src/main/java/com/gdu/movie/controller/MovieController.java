package com.gdu.movie.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gdu.movie.service.MovieService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MovieController {
  
  private final MovieService movieService;
  
  @GetMapping(value="/searchAllMovies", produces="application/json")
  public Map<String, Object> searchAllMovies(){
    return movieService.getMovieList();
  }
  
  @GetMapping(value="/searchMovie", produces="application/json")
  public Map<String, Object> searchMovies(HttpServletRequest request){
    return movieService.getMovieSearchResult(request);
  }
  
}
