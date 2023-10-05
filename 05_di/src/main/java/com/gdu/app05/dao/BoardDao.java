package com.gdu.app05.dao;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gdu.app05.dto.BoardDto;

@Repository
public class BoardDao {

  public List<BoardDto> getBoardList(){
    return Arrays.asList(
        new BoardDto("김민지", "김민지"),
        new BoardDto("팜하니", "팜하니"),
        new BoardDto("강해린", "강해린")
    );
  }
}
