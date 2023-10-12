package com.gdu.app12.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.app12.dto.ContactDto;
import com.gdu.app12.service.ContactService;

import lombok.RequiredArgsConstructor;

@RequestMapping(value="/contact")
@RequiredArgsConstructor
@Controller
public class ContactController {
  
  private final ContactService contactService;
  
  @GetMapping(value="/list.do")
  public String list(Model model) {
    List<ContactDto> contactList = contactService.getContactList();
    model.addAttribute("contactList", contactList);
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
    return "redirect:/contact/list.do";
  }
  
  @GetMapping(value="/detail.do")
  public String detail(@RequestParam(value="contactNo", required=false, defaultValue="0") int contactNo, Model model) {
    model.addAttribute("contactDto", contactService.getContactByNo(contactNo));
    return "contact/detail";
  }
  
  @PostMapping(value="/modify.do")
  public String modify(ContactDto contactDto, RedirectAttributes ra) {
    int modifyResult = contactService.modifyContact(contactDto);
    ra.addFlashAttribute("modifyResult", modifyResult);
    return "redirect:/contact/detail.do?contactNo=" + contactDto.getContactNo();
  }
  
  @PostMapping(value="/delete.do")
  public String modify(@RequestParam(value="contactNo", required=false, defaultValue="0") int contactNo, RedirectAttributes ra) {
    int deleteResult = contactService.deleteContact(contactNo);
    ra.addFlashAttribute("deleteResult", deleteResult);
    return "redirect:/contact/list.do";
  }
  
  @GetMapping(value="tx.do")
  public String txTest() {
    try {
      contactService.txTest();
    } catch(Exception e) {
      e.printStackTrace();
    }
    return "index";
  }
  
}
