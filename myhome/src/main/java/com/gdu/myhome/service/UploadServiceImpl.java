package com.gdu.myhome.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.myhome.dao.UploadMapper;
import com.gdu.myhome.dto.AttachDto;
import com.gdu.myhome.dto.UploadDto;
import com.gdu.myhome.dto.UserDto;
import com.gdu.myhome.util.MyFileUtils;
import com.gdu.myhome.util.MyPageUtils;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;

@Transactional
@RequiredArgsConstructor
@Service
public class UploadServiceImpl implements UploadService {

  private final UploadMapper uploadMapper;
  private final MyFileUtils myFileUtils;
  private final MyPageUtils myPageUtils;
  
  @Override
  public boolean addUpload(MultipartHttpServletRequest multipartRequest) throws Exception {
    
    String title = multipartRequest.getParameter("title");
    String contents = multipartRequest.getParameter("contents");
    int userNo = Integer.parseInt(multipartRequest.getParameter("userNo"));
    
    UploadDto upload = UploadDto.builder()
                        .title(title)
                        .contents(contents)
                        .userDto(UserDto.builder()
                                  .userNo(userNo)
                                  .build())
                        .build();
    
    int uploadCount = uploadMapper.insertUpload(upload);
    
    List<MultipartFile> files = multipartRequest.getFiles("files");
    
    int attachCount;
    if(files.get(0).getSize() == 0) {
      attachCount = 1;
    } else {
      attachCount = 0;
    }
    
    for(MultipartFile multipartFile : files) {
      
      if(multipartFile != null && !multipartFile.isEmpty()) {
        
        String path = myFileUtils.getUploadPath();
        File dir = new File(path);
        if(!dir.exists()) {
          dir.mkdirs();
        }
        
        String originalFilename = multipartFile.getOriginalFilename();
        String filesystemName = myFileUtils.getFilesystemName(originalFilename);
        File file = new File(dir, filesystemName);
        
        multipartFile.transferTo(file);
        
        String contentType = Files.probeContentType(file.toPath());  // 이미지의 Content-Type은 image/jpeg, image/png 등 image로 시작한다.
        int hasThumbnail = (contentType != null && contentType.startsWith("image")) ? 1 : 0;
        
        if(hasThumbnail == 1) {
          File thumbnail = new File(dir, "s_" + filesystemName);  // small 이미지를 의미하는 s_을 덧붙임
          Thumbnails.of(file)
                    .size(100, 100)      // 가로 100px, 세로 100px
                    .outputFormat("jpg")
                    .toFile(thumbnail);
        }
        
        AttachDto attach = AttachDto.builder()
                            .path(path)
                            .originalFilename(originalFilename)
                            .filesystemName(filesystemName)
                            .hasThumbnail(hasThumbnail)
                            .uploadNo(upload.getUploadNo())
                            .build();
        
        attachCount += uploadMapper.insertAttach(attach);
        
      }  // if
      
    }  // for
    
    return (uploadCount == 1) && (files.size() == attachCount);
    
  }
  
  @Override
  public Map<String, Object> getUploadList(HttpServletRequest request) {
    
    Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(opt.orElse("1"));
    int total = uploadMapper.getUploadCount();
    int display = 9;
    
    myPageUtils.setPaging(page, total, display);
    
    Map<String, Object> map = Map.of("begin", myPageUtils.getBegin(),
                                     "end", myPageUtils.getEnd());
    List<UploadDto> uploadList = uploadMapper.getUploadList(map);
    
    return Map.of("uploadList", uploadList,
                  "totalPage", myPageUtils.getTotalPage());
    
  }
  
  @Override
  public void loadUpload(HttpServletRequest request, Model model) {
    int uploadNo = Integer.parseInt(request.getParameter("uploadNo"));
    UploadDto upload = uploadMapper.getUpload(uploadNo);
    List<AttachDto> attachList = uploadMapper.getAttachList(uploadNo);
    model.addAttribute("upload", upload);
    model.addAttribute("attachList", attachList);
  }
  
  @Override
  public ResponseEntity<Resource> downloadAttach(HttpServletRequest request) {
    int attachNo = Integer.parseInt(request.getParameter("attachNo"));
    AttachDto attach = uploadMapper.getAttach(attachNo);
   
    File file = new File(attach.getPath(), attach.getFilesystemName());
    Resource resource = new FileSystemResource(file);
        
    if(!file.exists()) {
      return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
    }
    
    uploadMapper.increaseDownloadCount(attachNo);
    
    String originalFilename = attach.getOriginalFilename();
    String userAgent = request.getHeader("User-Agent");
    try {
      if(userAgent.contains("Trident")) {
        originalFilename = URLEncoder.encode(originalFilename, "UTF-8").replace("+", " ");
      }
      else if(userAgent.contains("Edge")) {
        originalFilename = URLEncoder.encode(originalFilename, "UTF-8");
      }
      else {
        originalFilename = new String(originalFilename.getBytes("UTF-8"), "ISO-8859-1");
      } 
    } catch(Exception e) {
        e.printStackTrace();
    }
    
    HttpHeaders header = new HttpHeaders();
    header.add("Content-type", "application/octet-stream");
    header.add("Content-Disposition", "attachment; filename=" + originalFilename);
    header.add("Content-Length", file.length() + "");

    return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
    
  }
  
  @Override
  public ResponseEntity<Resource> downloadAll(HttpServletRequest request) {

    int uploadNo = Integer.parseInt(request.getParameter("uploadNo"));
    List<AttachDto> attachList = uploadMapper.getAttachList(uploadNo);
    
    if(attachList.isEmpty()) {
      return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
    }
    
    File tempDir = new File(myFileUtils.getTempPath());
    if(!tempDir.exists()) {
      tempDir.mkdirs();
    }
    String zipName = myFileUtils.getTempFilename() + ".zip";
    File zipFile = new File(tempDir, zipName);
    
    try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipFile))) {
      for(AttachDto attach : attachList) {
        
        ZipEntry zipEntry = new ZipEntry(attach.getOriginalFilename());
        zout.putNextEntry(zipEntry);
        
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(new File(attach.getPath(), attach.getFilesystemName())));
        zout.write(bin.readAllBytes());
        
        bin.close();
        zout.closeEntry();
        
        uploadMapper.increaseDownloadCount(uploadNo);
      }
        
    } catch(Exception e) {
      e.printStackTrace();
    }
    
    Resource resource = new FileSystemResource(zipFile);

    HttpHeaders header = new HttpHeaders();
    header.add("Content-type", "application/octet-stream");
    header.add("Content-Disposition", "attachment; filename=" + zipFile.getName());
    header.add("Content-Length", zipFile.length() + "");

    return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
    
  }
  
  @Override
  public void removeTempFiles() {
    File tempDir = new File(myFileUtils.getTempPath());
    File[] targetList = tempDir.listFiles();
    if(targetList != null) {
      for(File target : targetList) {
        target.delete();
      }
    }
  }
  
}