package com.gdu.app12.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.app12.dto.ContactDto;

@Mapper
public interface ContactMapper {
  public int insert(ContactDto contactDto);
  public int update(ContactDto contactDto);
  public int delete(int contactNo);
  public List<ContactDto> selectList();
  public ContactDto selectContactByNo(int contactNo);
  public int deleteOldestContact();
}