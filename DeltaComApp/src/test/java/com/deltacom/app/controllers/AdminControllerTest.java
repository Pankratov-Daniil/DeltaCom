package com.deltacom.app.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:spring-config-test.xml")
@WithUserDetails(userDetailsServiceBeanName = "ClientLoginSecurity", value = "admin@admin.com")
public class AdminControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() throws Exception {
        mockMvc =  MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void index() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/index"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("admin/index"));
    }

    @Test
    public void grantAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/grantAccess"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("admin/grantAccess"));
    }

}