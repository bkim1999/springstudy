package com.gdu.app06.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gdu.app06.dao.BoardDao;
import com.gdu.app06.dto.BoardDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
  
  private final BoardDao boardDao;

  @Override
  public List<BoardDto> getBoardList() {
    return boardDao.getBoardList();
  }
  
  @Override
  public BoardDto getBoardByNo(int boardNo) {
    return boardDao.getBoardByNo(boardNo);
  }
  
}
