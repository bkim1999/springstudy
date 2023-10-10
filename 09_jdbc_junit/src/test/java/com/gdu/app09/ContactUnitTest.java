package com.gdu.app09;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gdu.app09.dao.ContactDao;
import com.gdu.app09.dto.ContactDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ContactUnitTest {
  
  @Autowired
  private ContactDao contactDao;
  
  @Test
  public void test01() {
    ContactDto contactDto = new ContactDto(0, "김민지", "010-김민지", "김민지@gmail.com", "뉴진스로", "");
    int insertResult = contactDao.insert(contactDto);
    assertEquals(1, insertResult);
  }
  
  @Test
  public void test02() {
    int contact_no = 1;
    ContactDto contactDto = contactDao.selectContactByNo(contact_no);
    assertNotNull(contactDto);
  }
  
  @Test
  public void test03() {
    int contact_no = 1;
    int deleteResult = contactDao.delete(contact_no);
    assertEquals(1, deleteResult);
    assertNull(contactDao.selectContactByNo(contact_no));
  }

}
