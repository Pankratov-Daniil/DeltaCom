package com.deltacom.app.controllers;

import com.deltacom.app.entities.ClientCart;
import com.deltacom.app.services.api.ClientService;
import com.deltacom.app.services.api.ContractService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:spring-config-test.xml")
@WithMockUser(username = "mobigod0@gmail.com")
public class ClientControllerTest {
    @Autowired
    private ClientService clientService;
    @Autowired
    private ContractService contractService;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() throws Exception {
        mockMvc =  MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void index() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/index"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("user/index"));
    }

    @Test
    public void contracts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/contracts"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("user/contracts"));
    }

    @Test
    public void getCurrentClient() throws Exception {
        User user = new User("mobigod0@gmail.com", "123", AuthorityUtils.createAuthorityList("ROLE_USER"));
        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(user,null);
        assertEquals(mockMvc.perform(MockMvcRequestBuilders.get("/user/getCurrentClient")
                        .principal(testingAuthenticationToken))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(),
                new ObjectMapper().writeValueAsString(clientService.getClientByEmail("mobigod0@gmail.com"))
                );
    }

    @Test
    public void getContractByNumber() throws Exception {
        assertEquals(mockMvc.perform(MockMvcRequestBuilders.get("/user/getContractByNumber")
                        .param("number", "89222222222"))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString(),
                new ObjectMapper().writeValueAsString(contractService.getContractByNumber("89222222222"))
        );
    }

    @Test
    @Rollback
    public void changeContract() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/changeContract")
                .param("numberModal", "89222222222")
                .param("selectTariff", "2")
                .param("selectOptions", "1"))
        .andExpect(view().name("redirect:/user/contracts"));
    }

    @Test
    @Rollback
    public void blockContract() throws Exception {
        assertEquals(mockMvc.perform(MockMvcRequestBuilders.get("/user/blockContract")
                        .param("contractId", "21")
                        .param("block", "true")
                        .param("blockedByOperator", "false"))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString(),
                    "true");
    }

    @Test
    public void saveAndGetCart() throws Exception {
        assertEquals(mockMvc.perform(MockMvcRequestBuilders.get("/user/saveCart")
                    .param("numberModal", "89222222222")
                    .param("selectTariff", "2"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(),
                new ObjectMapper().writeValueAsString(new ClientCart("89222222222", "2", new String[0])));
    }

    @Test
    public void getCart() throws Exception {
        ClientCart clientCart = new ClientCart("89222222222", "2", new String[0]);
        assertEquals(mockMvc.perform(MockMvcRequestBuilders.get("/user/getCart").sessionAttr("cart", clientCart))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                new ObjectMapper().writeValueAsString(clientCart));
    }

    @Test
    public void removeCart() throws Exception {
        ClientCart clientCart = new ClientCart("89222222222", "2", new String[0]);
        mockMvc.perform(MockMvcRequestBuilders.get("/user/removeCart").sessionAttr("cart", clientCart))
                        .andExpect(status().isOk());
    }
}