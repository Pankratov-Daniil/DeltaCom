package com.deltacom.app.entities;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-test.xml")
public class ClientTest {
    Client client;

    @Before
    public void setUp() throws Exception {
        List<AccessLevel> accessLevelList = Arrays.asList(new AccessLevel(1, "User"), new AccessLevel(2, "Manager"));
        List<Contract> contracts = Arrays.asList(new Contract(), new Contract(), new Contract());
        client = new Client(5, "Daniil", "Pankratov", new Date(29, 6, 1995), "passport", "address", "a@gm.com", "pass", accessLevelList, contracts);
    }

    @Test
    public void getId() throws Exception {
        assertEquals(client.getId(), 5);
    }

    @Test
    public void setId() throws Exception {
        client.setId(9);
        assertEquals(client.getId(), 9);
    }

    @Test
    public void getFirstName() throws Exception {
        assertEquals(client.getFirstName(), "Daniil");
    }

    @Test
    public void setFirstName() throws Exception {
        client.setFirstName("NotDaniil");
        assertEquals(client.getFirstName(), "NotDaniil");
    }

    @Test
    public void getLastName() throws Exception {
        assertEquals(client.getLastName(), "Pankratov");
    }

    @Test
    public void setLastName() throws Exception {
        client.setLastName("Pankratov-Black");
        assertEquals(client.getLastName(), "Pankratov-Black");
    }

    @Test
    public void getBirthDate() throws Exception {
        assertEquals(client.getBirthDate(), new Date(29, 6, 1995));
    }

    @Test
    public void setBirthDate() throws Exception {
        client.setBirthDate(new Date(01,01,1970));
        assertEquals(client.getBirthDate(), new Date(1,1,1970));
    }

    @Test
    public void getPassport() throws Exception {
        assertEquals(client.getPassport(), "passport");
    }

    @Test
    public void setPassport() throws Exception {
        client.setPassport("notPassport");
        assertEquals(client.getPassport(), "notPassport");
    }

    @Test
    public void getAddress() throws Exception {
        assertEquals(client.getAddress(), "address");
    }

    @Test
    public void setAddress() throws Exception {
        client.setAddress("newAddress");
        assertEquals(client.getAddress(), "newAddress");
    }

    @Test
    public void getEmail() throws Exception {
        assertEquals(client.getEmail(), "a@gm.com");
    }

    @Test
    public void setEmail() throws Exception {
        client.setEmail("newEmail");
        assertEquals(client.getEmail(), "newEmail");
    }

    @Test
    public void getPassword() throws Exception {
        assertEquals(client.getPassword(), "pass");
    }

    @Test
    public void setPassword() throws Exception {
        client.setPassword("newPass");
        assertEquals(client.getPassword(), "newPass");
    }

    @Test
    public void getAccessLevels() throws Exception {
        assertTrue(client.getAccessLevels().size() == 2);
    }

    @Test
    public void setAccessLevels() throws Exception {
        client.setAccessLevels(null);
        assertNull(client.getAccessLevels());
    }

    @Test
    public void getContracts() throws Exception {
        assertTrue(client.getContracts().size() == 3);
    }

    @Test
    public void setContracts() throws Exception {
        client.setContracts(null);
        assertNull(client.getContracts());
    }

    @Test
    public void toStringTest() throws Exception {
        assertEquals(client.toString(), "Client{id=5, firstName='Daniil', lastName='Pankratov', birthDate=Sun Dec 16 00:00:00 MSK 1934, passport='passport', address='address', email='a@gm.com', password='pass', accessLevels=[AccessLevel{id=1, name='User'}, AccessLevel{id=2, name='Manager'}]}");
    }

    @Test
    public void equalsHashCodeTest() throws Exception {
        List<AccessLevel> accessLevelList = Arrays.asList(new AccessLevel(1, "User"), new AccessLevel(2, "Manager"));
        List<Contract> contracts = Arrays.asList(new Contract(), new Contract(), new Contract());
        Client equalClient = new Client(5, "Daniil", "Pankratov", new Date(29, 06, 1995), "passport", "address", "a@gm.com", "pass", accessLevelList, contracts);

        assertTrue(client.equals(equalClient));
        assertEquals(client.hashCode(), equalClient.hashCode());
    }


}