package com.gdu.app13.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.app13.util.MyFileUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {

  private final MyFileUtil myFileUtil;
  
  @Override
  public int upload(MultipartHttpServletRequest multiPartRequest) {

    List<MultipartFile> files = multiPartRequest.getFiles("files");
    
    for(MultipartFile multipartFile : files) {
      
      if(multipartFile != null && !multipartFile.isEmpty()) {
        try {
          String path = myFileUtil.getPath();
          File dir = new File(path);
          if(!dir.exists()) {
            dir.mkdirs();
          }
          
          String originalName = multipartFile.getOriginalFilename();
          String filesystemName = myFileUtil.getFilesystemName(originalName);
          File file = new File(dir, filesystemName);
          
          multipartFile.transferTo(file);
          
        } catch(Exception e) {
          e.printStackTrace();
        }
      
      }
    
    }
    
    return 0;
    
  }
  
  @Override
  public Map<String, Object> ajaxUpload(MultipartHttpServletRequest multipartRequest) {
    List<MultipartFile> files = multipartRequest.getFiles("files");
    
    for(MultipartFile multipartFile : files) {
      
      if(multipartFile != null && !multipartFile.isEmpty()) {
        try {
          String path = myFileUtil.getPath();
          File dir = new File(path);
          if(!dir.exists()) {
            dir.mkdirs();
          }
          
          String originalName = multipartFile.getOriginalFilename();
          String filesystemName = myFileUtil.getFilesystemName(originalName);
          File file = new File(dir, filesystemName);
          
          multipartFile.transferTo(file);
          
        } catch(Exception e) {
          e.printStackTrace();
        }
      
      }
    
    }
    return Map.of("success", true);
    
  }
  
  @Override
  public Map<String, Object> ckeditorUpload(MultipartFile upload, String contextPath) {

    String path = myFileUtil.getPath();
    File dir = new File(path);
    if(!dir.exists()) {
      dir.mkdirs();
    }

    String originalName = upload.getOriginalFilename();
    String filesystemName = myFileUtil.getFilesystemName(originalName);
    File file = new File(dir, filesystemName);
    try {
      upload.transferTo(file);
    } catch(Exception e) {
      e.printStackTrace();
    }
    
    return Map.of("url", contextPath + path + "/" + filesystemName
                , "uploaded", true);
    
  }

}
