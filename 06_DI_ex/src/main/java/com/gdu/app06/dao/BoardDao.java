package com.gdu.app06.dao;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gdu.app06.dto.BoardDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BoardDao {
  
  private final BoardDto boardDto1;
  private final BoardDto boardDto2;
  private final BoardDto boardDto3;
  
  public List<BoardDto> getBoardList(){
    return Arrays.asList(boardDto1, boardDto2, boardDto3);
  }
  
  public BoardDto getBoardByNo(int boardNo) {
    BoardDto boardDto = null;
    if(boardDto1.getBoardNo() == boardNo) {
      boardDto = boardDto1;
    } else if(boardDto2.getBoardNo() == boardNo) {
      boardDto = boardDto2;
    } else {
      boardDto = boardDto3;
    }
    return boardDto;
  }
  
}
