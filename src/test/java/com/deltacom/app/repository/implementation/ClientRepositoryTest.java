package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.Client;
import com.deltacom.app.exceptions.RepositoryException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-test.xml")
public class ClientRepositoryTest {
    @Autowired
    private ClientRepositoryImpl clientRepository;

    @Test
    public void getClientByEmail() {
        Client client = clientRepository.getClientByEmail("mobigod0@gmail.com");
        assertNotNull(client);
        assertEquals(client.getAddress(), "адрес");
    }

    @Test
    public void getClientsByIds() {
        List<Client> tenClients = clientRepository.getClientsFromIndex(0, 10);
        List<Client> twoClients = clientRepository.getClientsFromIndex(11, 10);
        List<Client> zeroClients = clientRepository.getClientsFromIndex(50, 25);

        assertNotNull(tenClients);
        assertEquals(tenClients.size(), 10);
        assertNotNull(twoClients);
        assertEquals(twoClients.size(), 0);
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

    @Test
    @Rollback
    public void addTest() {
        clientRepository.add(new Client("", "", new Date(1,1,1980), "pass", "addr", "one@emai.cl", "passwd", null));
        clientRepository.add(new Client("", "", new Date(1,1,1980), "pass", "addr", "two@emai.cl", "passwd", null));

        assertEquals(clientRepository.getAll().size(), 15);
    }

    @Test(expected = RepositoryException.class)
    @Rollback
    public void addTestException() {
        clientRepository.add(new Client("", "", new Date(1,1,1980), "pass", "addr", "one@emai.cl", "passwd", null));
        clientRepository.add(new Client("", "", new Date(1,1,1980), "pass", "addr", "one@emai.cl", "passwd", null));
    }

    @Test
    @Rollback
    public void updateTest() {
        Client existingClient = new Client("Даниил","Панкратов",new Date(29, 6, 1995),"паспорт","адрес","mobigod0@gmail.com","newPass", null);
        existingClient.setId(5);
        clientRepository.update(existingClient);

        assertEquals(clientRepository.getById(5).getPassword(), "newPass");
    }

    @Test
    @Rollback
    public void removeTest() {
        Client existingClient = new Client("Даниил","Панкратов",new Date(29, 6, 1995),"паспорт","адрес","mobigod0@gmail.com","newPass", null);
        existingClient.setId(5);
        clientRepository.remove(existingClient);

        assertEquals(clientRepository.getById(5), null);
    }

    @Test
    public void getByIdTest() {
        assertEquals(clientRepository.getById(5).getEmail(), "mobigod0@gmail.com");
        assertEquals(clientRepository.getById(954), null);
    }

    @Test
    public void getAllTest() {
        assertEquals(clientRepository.getAll().size(), 13);
    }
}