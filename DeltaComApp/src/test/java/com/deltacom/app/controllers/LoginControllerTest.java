package com.deltacom.app.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:spring-config-test.xml")
public class LoginControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() throws Exception {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        mockMvc =  MockMvcBuilders.standaloneSetup(new LoginController())
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    @WithAnonymousUser
    public void loginAnonymous() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/process-login"))
                .andExpect(status().isOk())
                .andExpect(view().name("loginPage"));
    }


    @Test
    @WithMockUser(username = "admin@admin.com", authorities = {"ADMIN"})
    public void loginAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/process-login"))
                .andExpect(view().name("redirect:/admin/index"));
    }

    @Test
    @WithMockUser(username = "manager@manager.com", authorities = {"MANAGER"})
    public void loginManager() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/process-login"))
                .andExpect(view().name("redirect:/manager/index"));
    }

    @Test
    @WithMockUser(username = "user@user.com", authorities = {"USER"})
    public void loginUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/process-login"))
                .andExpect(view().name("redirect:/user/index"));
    }
}