package com.gdu.myhome.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.myhome.dto.BlogDto;

public interface BlogService {
  public Map<String, Object> imageUpload(MultipartHttpServletRequest multipartRequest);
  public int addBlog(HttpServletRequest request);
  public void blogImageBatch();
  public List<BlogDto> getBlogList(HttpServletRequest request);
}
