package com.gdu.myhome.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.myhome.dao.FreeMapper;
import com.gdu.myhome.dto.FreeDto;
import com.gdu.myhome.util.MySecurityUtils;
import com.gdu.myhome.util.PageUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FreeServiceImpl implements FreeService {

  private final FreeMapper freeMapper;
  private final MySecurityUtils mySecurityUtils;
  private final PageUtil pageUtil;
  
  @Override
  public int addFree(HttpServletRequest request) {
    
    String email = request.getParameter("email");
    String contents = mySecurityUtils.preventXSS(request.getParameter("contents"));
    
    FreeDto free = FreeDto.builder()
                    .email(email)
                    .contents(contents)
                    .build();

    return freeMapper.insertFree(free);
    
  }
  
  @Override
  public void loadFreeList(HttpServletRequest request, Model model) {

    Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(opt.orElse("1"));
    
    int display = 10;
    int total = freeMapper.getFreeCount();
    pageUtil.setPaging(page, total, display);
    
    int begin = pageUtil.getBegin();
    int end = pageUtil.getEnd();
    
    Map<String, Object> map = Map.of("begin", begin, "end", end);
    List<FreeDto> freeList = freeMapper.getFreeList(map);
    
    model.addAttribute("freeList", freeList);
    model.addAttribute("paging", pageUtil.getMvcPaging(request.getContextPath() + "/free/list.do?"));
    model.addAttribute("beginNo", total - (page - 1) * display);
    
  }
  
  @Transactional
  @Override
  public int addReply(HttpServletRequest request) {

    // 원글
    int depth = Integer.parseInt(request.getParameter("depth"));
    int groupNo = Integer.parseInt(request.getParameter("groupNo"));
    int groupOrder = Integer.parseInt(request.getParameter("groupOrder"));
    
    // 댓글
    String email = request.getParameter("email");
    String contents = request.getParameter("contents");
    
    
    FreeDto free = FreeDto.builder()
                          .depth(depth)
                          .groupNo(groupNo)
                          .groupOrder(groupOrder)
                          .build();
    
    freeMapper.updateGroupOrder(free);
    
    FreeDto reply = FreeDto.builder()
                           .email(email)
                           .contents(contents)
                           .depth(depth + 1)
                           .groupNo(groupNo)
                           .groupOrder(groupOrder + 1)
                           .build();
    System.out.println(free);
    System.out.println(reply);
    
    return freeMapper.insertReply(reply);
    
  }
  
  @Override
  public int removeFree(int freeNo) {
    return freeMapper.removeFree(freeNo);
  }
  
  @Override
  public void loadSearchList(HttpServletRequest request, Model model) {
    
    String column = request.getParameter("column");
    String query = request.getParameter("query");
    Map<String, Object> map = new HashMap<String, Object>(); 
    map.put("column", column);
    map.put("query", query);

    Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(opt.orElse("1"));
    int display = 10;
    int total = freeMapper.getSearchCount(map);
    pageUtil.setPaging(page, total, display);
    
    int begin = pageUtil.getBegin();
    int end = pageUtil.getEnd();
    map.put("begin", begin);
    map.put("end", end);
    System.out.println("map" + map);
    List<FreeDto> freeList = freeMapper.getSearchList(map);
    System.out.println("freeList" + freeList);
    
    model.addAttribute("freeList", freeList);
    model.addAttribute("paging", pageUtil.getMvcPaging(request.getContextPath() + "/free/search.do?column=" + column + "&query=" + query + "&"));
    model.addAttribute("beginNo", total - (page - 1) * display);
    
  }
  
}
