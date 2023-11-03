package com.gdu.myhome.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UploadDto {
  private int uploadNo;
  private String title;
  private String contents;
  private String createdAt;
  private String modifiedAt;
  private int attachCount; // UPLOAD_에는 없음 (목록보기용)
  private UserDto userDto; // userNo
}
