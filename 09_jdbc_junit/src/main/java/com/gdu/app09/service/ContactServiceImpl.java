package com.gdu.app09.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gdu.app09.dao.ContactDao;
import com.gdu.app09.dto.ContactDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ContactServiceImpl implements ContactService {
  
  private final ContactDao contactDao;
  
  @Override
  public int addContact(ContactDto contactDto) {
    return contactDao.insert(contactDto);
  }

  @Override
  public int modifyContact(ContactDto contactDto) {
    return contactDao.update(contactDto);
  }

  @Override
  public int deleteContact(int contact_no) {
    return contactDao.delete(contact_no);
  }

  @Override
  public List<ContactDto> getContactList() {
    return contactDao.selectList();
  }

  @Override
  public ContactDto getContactByNo(int contact_no) {
    return contactDao.selectContactByNo(contact_no);
  }

}
