package com.deltacom.app.entities;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

public class ClientCartTest {
    private ClientCart clientCart;

    @Before
    public void setUp() throws Exception {
        clientCart = new ClientCart("8921", "6", new String[]{"1", "2"});
    }

    @Test
    public void getNumber() throws Exception {
        assertEquals(clientCart.getNumber(), "8921");
    }

    @Test
    public void setNumber() throws Exception {
        clientCart.setNumber("000");
        assertEquals(clientCart.getNumber(), "000");
    }

    @Test
    public void getTariffId() throws Exception {
        assertEquals(clientCart.getTariffId(), "6");
    }

    @Test
    public void setTariffId() throws Exception {
        clientCart.setTariffId("999");
        assertEquals(clientCart.getTariffId(), "999");
    }

    @Test
    public void getOptionsIds() throws Exception {
        assertTrue(clientCart.getOptionsIds().length == 2);
    }

    @Test
    public void setOptionsIds() throws Exception {
        clientCart.setOptionsIds(new String[]{"0", "2", "8", "93"});
        assertTrue(clientCart.getOptionsIds().length == 4);
    }

    @Test
    public void toStringTest() throws Exception {
        assertEquals(clientCart.toString(), "ClientCart{number='8921', tariffId='6', optionsIds=[1, 2]}");
    }

    @Test
    public void equalsHashCodeTest() throws Exception {
        ClientCart equalClientCart = new ClientCart("8921", "6", new String[]{"1", "2"});
        ClientCart notEqualClientCart = new ClientCart(null, null, null);

        assertTrue(clientCart.equals(equalClientCart));
        assertEquals(clientCart.hashCode(), equalClientCart.hashCode());

        assertTrue(clientCart.equals(clientCart));
        assertEquals(clientCart.hashCode(), clientCart.hashCode());
        assertFalse(clientCart.equals(null));
        assertFalse(clientCart.equals(new Integer(2)));
        assertFalse(clientCart.equals(notEqualClientCart));
        assertNotEquals(clientCart.hashCode(), notEqualClientCart.hashCode());
        notEqualClientCart.setNumber("8921");
        assertFalse(clientCart.equals(notEqualClientCart));
        notEqualClientCart.setNumber("59648");
        assertFalse(clientCart.equals(notEqualClientCart));
        assertNotEquals(clientCart.hashCode(), notEqualClientCart.hashCode());
        notEqualClientCart.setTariffId("6");
        assertFalse(clientCart.equals(notEqualClientCart));
        notEqualClientCart.setTariffId("151");
        assertFalse(clientCart.equals(notEqualClientCart));
        assertNotEquals(clientCart.hashCode(), notEqualClientCart.hashCode());
    }

    @Test
    public void emptyConstructorTest() {
        ClientCart emptyClientCart = new ClientCart();
        assertEquals(emptyClientCart.getTariffId(), null);
        assertEquals(emptyClientCart.getNumber(), null);
        assertTrue(emptyClientCart.getOptionsIds() == null);
    }
}