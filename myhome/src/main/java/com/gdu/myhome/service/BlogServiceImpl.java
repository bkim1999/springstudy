package com.gdu.myhome.service;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.myhome.dao.BlogMapper;
import com.gdu.myhome.dto.BlogDto;
import com.gdu.myhome.dto.BlogImageDto;
import com.gdu.myhome.dto.CommentDto;
import com.gdu.myhome.dto.UserDto;
import com.gdu.myhome.util.MyFileUtils;
import com.gdu.myhome.util.MyPageUtils;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class BlogServiceImpl implements BlogService {
  
  private final BlogMapper blogMapper;
  private final MyFileUtils myFileUtils;
  private final MyPageUtils myPageUtils;
  
  @Override
  public Map<String, Object> imageUpload(MultipartHttpServletRequest multipartRequest) {
    
    // 이미지가 저장될 경로
    String imagePath = myFileUtils.getBlogImagePath();
    File dir = new File(imagePath);
    if(!dir.exists()) {
      dir.mkdirs();
    }
    
    // 이미지 파일 (CKEditor는 이미지를 upload라는 이름으로 보냄)
    MultipartFile upload = multipartRequest.getFile("upload");
    
    // 이미지가 저장될 이름
    String originalFilename = upload.getOriginalFilename();
    String filesystemName = myFileUtils.getFilesystemName(originalFilename);
    
    // 이미지 File 객체
    File file = new File(dir, filesystemName);
    
    // 저장
    try {
      upload.transferTo(file);
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    // CKEditor로 저장된 이미지의 경로를 JSON 형식으로 반환해야 함
    return Map.of("uploaded", true
                , "url", multipartRequest.getContextPath() + imagePath + "/" + filesystemName);
    
    // url: "http://localhost:8080/myhome/blog/2023/10/27/파일명"
    // sevlet-context.xml에
    // /blog/** 주소 요청을 /blog 디렉터리로 연결하는 <resources> 태그를 추가해야 함
    
  }
  
  @Override
  public int addBlog(HttpServletRequest request) {
    
    String title = request.getParameter("title");
    String contents = request.getParameter("contents");
    int userNo = Integer.parseInt(request.getParameter("userNo"));
    String ip = request.getRemoteAddr();
    
    BlogDto blog = BlogDto.builder()
                          .title(title)
                          .contents(contents)
                          .ip(ip)
                          .userDto(UserDto.builder().userNo(userNo).build())
                          .build();
    
    int addResult = blogMapper.insertBlog(blog);
    
    Document document = Jsoup.parse(contents);
    Elements elements = document.getElementsByTag("img");
    
    if(elements != null) {
      for(Element element : elements) {
        String src = element.attr("src");
        String filesystemName = src.substring(src.lastIndexOf("/") + 1);
        BlogImageDto blogImage = BlogImageDto.builder()
                                             .blogNo(blog.getBlogNo())
                                             .imagePath(myFileUtils.getBlogImagePath())
                                             .filesystemName(filesystemName)
                                             .build();
        blogMapper.insertBlogImage(blogImage);
      }
    }
    
    return addResult;
  }
  
  @Override
  public void blogImageBatch() {
    
    List<BlogImageDto> blogImageList = blogMapper.getBlogImageYesterady();
    List<Path> blogImagePathList = blogImageList.stream()
                                       .map(blogImageDto -> new File(blogImageDto.getImagePath(), blogImageDto.getFilesystemName()).toPath())
                                       .collect(Collectors.toList());
    
    File dir = new File(myFileUtils.getBlogImagePathYesterday());
    
    File[] targets = dir.listFiles(file -> !blogImagePathList.contains(file.toPath()));
    
    if(targets != null && targets.length != 0) {
      for(File file : targets) {
        file.delete();
      }
    }
    
  }
  
  @Transactional(readOnly=true)
  @Override
  public void loadBlogList(HttpServletRequest request, Model model) {
    
    Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(opt.orElse("1"));
    int total = blogMapper.getBlogCount();
    int display = 10;
    
    myPageUtils.setPaging(page, total, display);
    int begin = myPageUtils.getBegin();
    int end = myPageUtils.getEnd();
    Map<String, Object> map = Map.of("begin", begin, "end", end);
    
    List<BlogDto> blogList = blogMapper.getBlogList(map);
    model.addAttribute("blogList", blogList);
    model.addAttribute("paging", myPageUtils.getMvcPaging(request.getContextPath() + "/blog/list.do"));
    model.addAttribute("beginNo", total - (page - 1) * display);
    
  }
  
  @Override
  public int increaseHit(int blogNo) {
    return blogMapper.updateHit(blogNo);
  }
  
  @Override
  public BlogDto getBlog(int blogNo) {
    return blogMapper.getBlog(blogNo);
  }
  
  @Override
  public Map<String, Object> addComment(HttpServletRequest request) {
    
    String contents = request.getParameter("contents");
    int userNo = Integer.parseInt(request.getParameter("userNo"));
    int blogNo = Integer.parseInt(request.getParameter("blogNo"));
    
    CommentDto comment = CommentDto.builder()
                                   .contents(contents)
                                   .blogNo(blogNo)
                                   .userDto(UserDto.builder().userNo(userNo).build())
                                   .build();
    
    int addCommentResult = blogMapper.insertComment(comment);
    
    return Map.of("addCommentResult", addCommentResult);
    
  }
  
  @Override
  public int modifyBlog(HttpServletRequest request) {
    
    String title = request.getParameter("title");
    String contents = request.getParameter("contents");
    int blogNo = Integer.parseInt(request.getParameter("blogNo"));
    
    BlogDto blog = BlogDto.builder()
                          .title(title)
                          .contents(contents)
                          .blogNo(blogNo)
                          .build();
    
    return blogMapper.updateBlog(blog);
    
  }
  
  @Override
  public int removeBlog(int blogNo) {
    return blogMapper.deleteBlog(blogNo);
  }
  
  @Transactional(readOnly=true)
  @Override
  public Map<String, Object> loadCommentList(HttpServletRequest request) {
    
    int page = Integer.parseInt(request.getParameter("page"));
    int blogNo = Integer.parseInt(request.getParameter("blogNo"));
    int total = blogMapper.getCommentCount(blogNo);
    int display = 10;
    
    myPageUtils.setPaging(page, total, display);
    int begin = myPageUtils.getBegin();
    int end = myPageUtils.getEnd();
    Map<String, Object> map = Map.of("blogNo", blogNo, "begin", begin, "end", end);
    
    List<CommentDto> commentList = blogMapper.getCommentList(map);
    
    return Map.of("commentList", commentList, "paging", myPageUtils.getAjaxPaging());
    
  }
  
  @Override
  public Map<String, Object> addCommentReply(HttpServletRequest request) {
    
    String contents = request.getParameter("replyContents");
    int userNo = Integer.parseInt(request.getParameter("userNo"));
    int blogNo = Integer.parseInt(request.getParameter("blogNo"));
    int groupNo = Integer.parseInt(request.getParameter("groupNo"));
    String ip = request.getRemoteAddr();
    
    CommentDto comment = CommentDto.builder()
                                    .contents(contents)
                                    .blogNo(blogNo)
                                    .groupNo(groupNo)
                                    .userDto(UserDto.builder().userNo(userNo).build())
                                    .build();
    
    int addCommentReplyResult = blogMapper.insertCommentReply(comment);
    
    return Map.of("addCommentReplyResult", addCommentReplyResult) ;
    
  }
  
  @Override
  public Map<String, Object> removeComment(int blogNo) {
    int removeCommentResult = blogMapper.deleteComment(blogNo);
    return Map.of("removeCommentResult", removeCommentResult);
  }
  
}