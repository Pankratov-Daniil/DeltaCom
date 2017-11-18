package com.deltacom.app.entities;

import com.deltacom.app.utils.DTOConverter;
import com.deltacom.dto.ClientDTO;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class ClientDTOTest {
    private ClientDTO clientDTO;

    @Before
    public void setUp() throws Exception {
        clientDTO = new ClientDTO(2, "Daniil", "Pankratov", new Date(29,6,1995), "passp", "addr",
                "e@mail.com", "passwd", new String[]{"1"}, true, "", "", "");
    }

    @Test
    public void getId() throws Exception {
        assertEquals(clientDTO.getId(), 2);
    }

    @Test
    public void setId() throws Exception {
        clientDTO.setId(55);
        assertEquals(clientDTO.getId(), 55);
    }

    @Test
    public void getFirstName() throws Exception {
        assertEquals(clientDTO.getFirstName(), "Daniil");
    }

    @Test
    public void setFirstName() throws Exception {
        clientDTO.setFirstName("NO");
        assertEquals(clientDTO.getFirstName(), "NO");
    }

    @Test
    public void getLastName() throws Exception {
        assertEquals(clientDTO.getLastName(), "Pankratov");
    }

    @Test
    public void setLastName() throws Exception {
        clientDTO.setLastName("OhNo");
        assertEquals(clientDTO.getLastName(), "OhNo");
    }

    @Test
    public void getBirthDate() throws Exception {
        assertEquals(clientDTO.getBirthDate(), new Date(29,6,1995));
    }

    @Test
    public void setBirthDate() throws Exception {
        clientDTO.setBirthDate(new Date(1,1,1970));
        assertEquals(clientDTO.getBirthDate(), new Date(1,1,1970));
    }

    @Test
    public void getPassport() throws Exception {
        assertEquals(clientDTO.getPassport(), "passp");
    }

    @Test
    public void setPassport() throws Exception {
        clientDTO.setPassport("notPassp");
        assertEquals(clientDTO.getPassport(), "notPassp");
    }

    @Test
    public void getAddress() throws Exception {
        assertEquals(clientDTO.getAddress(), "addr");
    }

    @Test
    public void setAddress() throws Exception {
        clientDTO.setAddress("newAddr");
        assertEquals(clientDTO.getAddress(), "newAddr");
    }

    @Test
    public void getEmail() throws Exception {
        assertEquals(clientDTO.getEmail(), "e@mail.com");
    }

    @Test
    public void setEmail() throws Exception {
        clientDTO.setEmail("newmail");
        assertEquals(clientDTO.getEmail(), "newmail");
    }

    @Test
    public void getPassword() throws Exception {
        assertEquals(clientDTO.getPassword(), "passwd");
    }

    @Test
    public void setPassword() throws Exception {
        clientDTO.setPassword("newPasswd");
        assertEquals(clientDTO.getPassword(), "newPasswd");
    }

    @Test
    public void getAccessLevels() throws Exception {
        assertArrayEquals(clientDTO.getAccessLevels(), new String[]{"1"});
    }

    @Test
    public void setAccessLevels() throws Exception {
        clientDTO.setAccessLevels(null);
        assertNull(clientDTO.getAccessLevels());
    }

    @Test
    public void toStringTest() throws Exception {
        assertEquals(clientDTO.toString(), "ClientDTO{id=2, firstName='Daniil', lastName='Pankratov', birthDate=Sun Dec 16 00:00:00 MSK 1934, passport='passp', address='addr', email='e@mail.com', password='passwd', accessLevels=[1]}");
    }

    @Test
    public void toClientTest() {
        assertEquals(DTOConverter.ClientDTOToClient(clientDTO), new Client(2, "Daniil", "Pankratov", new Date(29,6,1995), "passp", "addr", "e@mail.com", "passwd", null, null));
    }

}