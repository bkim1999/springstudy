package com.gdu.myhome.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.myhome.dto.InactiveUserDto;
import com.gdu.myhome.dto.LeaveUserDto;
import com.gdu.myhome.dto.UserDto;

@Mapper
public interface UserMapper {
  public UserDto getUser(Map<String, Object> map);
  public LeaveUserDto getLeaveUser(Map<String, Object> map);
  public InactiveUserDto getInactiveUser(Map<String, Object> map);
  public int insertAccess(String email);
  public int insertUser(UserDto userDto);
  public int updateUser(UserDto userDto);
  public int updateUserPw(UserDto userDto);
  public int deleteUser(UserDto userDto);
  public int insertLeaveUser(UserDto userDto);
  public int insertInactiveUser();
  public int deleteUserForInactive();
  public int insertActiveUser(String email);
  public int deleteInactiveUser(String email);
}
