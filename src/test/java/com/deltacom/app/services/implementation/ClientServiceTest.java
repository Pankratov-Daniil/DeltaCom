package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.Client;
import com.deltacom.app.exceptions.ClientException;
import com.deltacom.app.exceptions.RepositoryException;
import com.deltacom.app.services.api.ClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-test.xml")
public class ClientServiceTest {
    @Autowired
    ClientService clientService;

    @Test
    public void getClientById() throws Exception {
        assertEquals(clientService.getClientById(5).getEmail(), "mobigod0@gmail.com");
        assertEquals(clientService.getClientById(456), null);
    }

    @Test
    public void getClientByEmail() throws Exception {
        assertEquals(clientService.getClientByEmail("mobigod0@gmail.com").getId(), 5);
        assertEquals(clientService.getClientByEmail("abra@cadabra.com"), null);
    }

    @Test
    @Rollback
    public void addNewClient() throws Exception {
        Client client = new Client("Dan", "Pankratov", new Date(1,1,1970), "passp", "addr", "e@mail.com", "passwd", null);
        clientService.addNewClient(client, new String[]{"1", "2"});

        assertEquals(clientService.getClientByEmail("e@mail.com").getAccessLevels().size(), 2);
    }

    @Test(expected = RepositoryException.class)
    @Rollback
    public void addNewClientException() {
        Client client = new Client("Dan", "Pankratov", new Date(1,1,1970), "passp", "addr", "mobigod0@gmail.com", "passwd", null);
        clientService.addNewClient(client, new String[]{"1", "2"});
    }

    @Test
    public void getClientsByIds() throws Exception {
        assertEquals(clientService.getClientsByIds(5, 10).size(), 10);
        assertEquals(clientService.getClientsByIds(32, 5).size(), 5);
        assertEquals(clientService.getClientsByIds(32, 10).size(), 5);
        assertEquals(clientService.getClientsByIds(50, 20).size(), 0);
    }

    @Test(expected = ClientException.class)
    public void getClientsByIdsException() {
        clientService.getClientsByIds(32, -1);
    }

    @Test
    public void getClientByNumber() throws Exception {
        assertEquals(clientService.getClientByNumber("89222222222").getEmail(), "mobigod0@gmail.com");
        assertEquals(clientService.getClientByNumber("89468956"), null);
    }

}