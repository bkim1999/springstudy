package com.gdu.app12.batch;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gdu.app12.service.ContactService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Component
public class ContactScheduler {
  
  private final ContactService contactService;
  
  @Scheduled(cron="0 0/1 * * * ?")
  public void 뉴진스() {
    contactService.deleteOldestContact();
    log.info("가장 오래된 연락처 삭제");
  }
}
