package com.gdu.app06.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gdu.app06.dto.BoardDto;
import com.gdu.app06.service.BoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardController {

  private final BoardService boardService;
  
  @RequestMapping(value="", method=RequestMethod.GET)
  public String index() {
    return "index";
  }
  
  @RequestMapping(value="/board/list.do", method=RequestMethod.GET)
  public String list(Model model) {
    model.addAttribute("boardList", boardService.getBoardList());
    return "board/list";
  }
  
  @RequestMapping(value="/board/detail.do", method=RequestMethod.GET)
  public String detail(int boardNo, Model model) {
    model.addAttribute("boardDto", boardService.getBoardByNo(boardNo));
    return "board/detail";
  }
  
}
