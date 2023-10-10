package com.gdu.app09.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.app09.dto.ContactDto;
import com.gdu.app09.service.ContactService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping(value="/contact")
@RequiredArgsConstructor
@Controller
@Slf4j
public class ContactController {
  
  
  //private static final Logger log = LoggerFactory.getLogger(ContactController.class);
  
  private final ContactService contactService;
  
  @GetMapping(value="/list.do")
  public String list(Model model) {
    List<ContactDto> contactList = contactService.getContactList();
    model.addAttribute("contactList", contactList);
    log.info("list: " + contactList.toString());
    return "contact/list";
  }
  
  @GetMapping(value="/write.do")
  public String write() {
    return "contact/write";
  }
  
  @PostMapping(value="/add.do")
  public String add(ContactDto contactDto, RedirectAttributes ra) {
    int addResult = contactService.addContact(contactDto);
    ra.addFlashAttribute("addResult", addResult);
    log.info("add: " + contactDto);
    return "redirect:/contact/list.do";
  }
  
  @GetMapping(value="/detail.do")
  public String detail(@RequestParam(value="contact_no", required=false, defaultValue="0") int contact_no, Model model) {
    model.addAttribute("contactDto", contactService.getContactByNo(contact_no));
    log.info("detail: " + contact_no);
    return "contact/detail";
  }
  
  @PostMapping(value="/modify.do")
  public String modify(ContactDto contactDto, RedirectAttributes ra) {
    log.info("modify: " + contactDto);
    int modifyResult = contactService.modifyContact(contactDto);
    ra.addFlashAttribute("modifyResult", modifyResult);
    return "redirect:/contact/detail.do?contact_no=" + contactDto.getContact_no();
  }
  
  @PostMapping(value="/delete.do")
  public String modify(@RequestParam(value="contact_no", required=false, defaultValue="0") int contact_no, RedirectAttributes ra) {
    log.info("delete: " + contact_no);
    int deleteResult = contactService.deleteContact(contact_no);
    ra.addFlashAttribute("deleteResult", deleteResult);
    return "redirect:/contact/list.do";
  }
  
}
