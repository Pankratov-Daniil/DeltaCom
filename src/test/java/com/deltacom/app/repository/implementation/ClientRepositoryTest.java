package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.Client;
import com.deltacom.app.repository.api.ClientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-test.xml")
public class ClientRepositoryTest {
    @Autowired
    ClientRepository clientRepository;

    @Test
    public void getClientByEmail() {
        Client client = clientRepository.getClientByEmail("mobigod0@gmail.com");
        assertNotNull(client);
        assertEquals(client.getAddress(), "адрес");
    }

    @Test
    public void getClientsByIds() {
        List<Client> tenClients = clientRepository.getClientsByIds(4, 10);
        List<Client> twoClients = clientRepository.getClientsByIds(44, 10);
        List<Client> zeroClients = clientRepository.getClientsByIds(50, 25);

        assertNotNull(tenClients);
        assertTrue(tenClients.size() == 10);
        assertNotNull(twoClients);
        assertTrue(twoClients.size() == 2);
        assertNotNull(zeroClients);
        assertTrue(zeroClients.isEmpty());
    }

    @Test
    public void getClientByNumber() {
        Client realClient = clientRepository.getClientByNumber("89222222222");
        Client noneClient = clientRepository.getClientByNumber("8922");

        assertNotNull(realClient);
        assertEquals(realClient.getFirstName(), "Даниил");
        assertNull(noneClient);
    }
}