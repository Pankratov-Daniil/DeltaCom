package com.deltacom.app.controllers;

import com.deltacom.app.entities.Client;
import com.deltacom.app.services.api.AccessLevelService;
import com.deltacom.app.services.api.ClientService;
import com.deltacom.app.services.api.OptionService;
import com.deltacom.app.services.api.TariffService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Date;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@EnableWebMvc
@ContextConfiguration(locations = "classpath:spring-config-test.xml")
@WithMockUser(username = "manager@manager.com", roles = {"MANAGER"})
public class CommonControllerTest {
    @Autowired
    ClientService clientService;
    @Autowired
    AccessLevelService accessLevelService;
    @Autowired
    OptionService optionService;
    @Autowired
    TariffService tariffService;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() throws Exception {
        mockMvc =  MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void addClient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/commons/addNewClient"))
                .andExpect(status().isOk())
                .andExpect(view().name("commons/addNewClient"));
    }

    @Test
    @Rollback
    public void regNewClient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/commons/regNewClient")
                .param("firstName", "new")
                .param("lastName", "user")
                .param("birthDate", "1999-10-10")
                .param("passport", "passp")
                .param("address", "addr")
                .param("email", "mesdga@mail.com")
                .param("password", "165481968198sads")
                .param("accessLevelsSelect", "1"))
        .andExpect(view().name("redirect:/manager/addNewContract"));
    }

    @Test
    public void getOptionsForContract() throws Exception {
        assertEquals(mockMvc.perform(MockMvcRequestBuilders.get("/commons/getOptionsForTariff").param("selectTariff", "1"))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                new ObjectMapper().writeValueAsString(optionService.getAllOptionsForTariff(1)));
    }

    @Test
    public void getAllOptions() throws Exception {
        assertEquals(mockMvc.perform(MockMvcRequestBuilders.get("/commons/getAllOptions"))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                new ObjectMapper().writeValueAsString(optionService.getAllOptions()));
    }

    @Test
    public void getAllTariffs() throws Exception {
        assertEquals(mockMvc.perform(MockMvcRequestBuilders.get("/commons/getAllTariffs"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(),
                new ObjectMapper().writeValueAsString(tariffService.getAllTariffs()));
    }

}