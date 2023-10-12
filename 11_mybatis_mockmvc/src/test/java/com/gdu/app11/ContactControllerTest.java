package com.gdu.app11;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="file:src\\main\\webapp\\WEB-INF\\spring\\appServlet\\servlet-context.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@WebAppConfiguration
@Slf4j
public class ContactControllerTest {
  
  @Autowired
  private WebApplicationContext webApplicationContext;
  
  private MockMvc mockMvc;
  
  @Before
  public void setUp() throws Exception {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }
  
  @Test
  public void test01_insert() throws Exception {
    log.info(mockMvc
              .perform(MockMvcRequestBuilders
                        .post("/contact/add.do")
                        .param("name", "김민지")
                        .param("tel", "여신")
                        .param("email", "goddess@")
                        .param("address", "뉴진스로")
              )
              .andReturn()
              .getFlashMap()
              .toString()
    );
  }
  
  @Test
  public void test02_select() throws Exception {
    log.info(mockMvc
              .perform(MockMvcRequestBuilders
                        .get("/contact/detail.do")
                        .param("contactNo", "14")
              )
              .andReturn()
              .getModelAndView()
              .getModelMap()
              .toString()
    );
  }
  
}
