package com.gdu.app12.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.app12.dao.ContactMapper;
import com.gdu.app12.dto.ContactDto;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class ContactServiceImpl implements ContactService {
  
  private final ContactMapper contactDao;
  
  @Override
  public int addContact(ContactDto contactDto) {
    return contactDao.insert(contactDto);
  }

  @Override
  public int modifyContact(ContactDto contactDto) {
    return contactDao.update(contactDto);
  }

  @Override
  public int deleteContact(int contactNo) {
    return contactDao.delete(contactNo);
  }

  @Transactional(readOnly=true)
  @Override
  public List<ContactDto> getContactList() {
    return contactDao.selectList();
  }

  @Transactional(readOnly=true)
  @Override
  public ContactDto getContactByNo(int contactNo) {
    return contactDao.selectContactByNo(contactNo);
  }

  @Override
  public void txTest() {
    contactDao.insert(new ContactDto(0, "김민지", "010", "김민지@", "뉴진스로", null));
    contactDao.insert(new ContactDto());
  }
  
  @Override
  public void deleteOldestContact() {
    contactDao.deleteOldestContact();
  }
  
}
