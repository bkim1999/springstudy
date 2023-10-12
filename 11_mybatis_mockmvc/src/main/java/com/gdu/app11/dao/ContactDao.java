package com.gdu.app11.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.gdu.app11.dto.ContactDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ContactDao {

  private final SqlSessionTemplate sqlSessionTemplate;
  private final String NS = "mybatis.mapper.contact.";
  
  /**
   * 삽입 메소드<br>
   * @param contactDto 삽입할 연락처 정보(name, tel, email, address)
   * @return insertCount 삽입된 행(Row)의 개수, 1이면 삽입 성공, 0이면 삽입 실패
   */
  public int insert(final ContactDto contactDto) {
    return sqlSessionTemplate.insert(NS + "insert", contactDto); 
  }
  
  /**
   * 수정 메소드<br>
   * @param contactDto 수정할 연락처 정보(contact_no, name, tel, email, address)
   * @return updateCount 수정된 행(Row)의 개수, 1이면 수정 성공, 0이면 수정 실패
   */
  public int update(final ContactDto contactDto) {
    return sqlSessionTemplate.update(NS + "update", contactDto);
  }
  
  /**
   * 삭제 메소드<br>
   * @param contact_no 삭제할 연락처 번호
   * @return deleteCount 삭제된 행(Row)의 개수, 1이면 삭제 성공, 0이면 삭제 실패
   */
  public int delete(final int contactNo) {
    return sqlSessionTemplate.delete(NS + "delete", contactNo);
  }
  
  /**
   * 전체 조회 메소드<br>
   * @return 조회된 모든 연락처 정보(ContactDto)
   */
  public List<ContactDto> selectList() {
    return sqlSessionTemplate.selectList(NS + "selectList");
  }
  
  /**
   * 상세 조회 메소드<br>
   * @param contact_no 조회할 연락처 번호
   * @return contactDto 조회된 연락처 정보, 조회된 연락처가 없으면 null 반환
   */
  public ContactDto selectContactByNo(final int contactNo) {
    return sqlSessionTemplate.selectOne(NS + "selectContactByNo", contactNo);
  }
  
}