package com.deltacom.app.entities;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class TariffTest {
    Tariff tariff;

    @Before
    public void setUp() throws Exception {
        List<Option> options = Arrays.asList(new Option(2, "FirstOpt", 0.0f, 2.0f, null, null));
        tariff = new Tariff(60, "OurTariff", 20.f, options);
    }

    @Test
    public void getId() throws Exception {
        assertEquals(tariff.getId(), 60);
    }

    @Test
    public void setId() throws Exception {
        tariff.setId(5);
        assertEquals(tariff.getId(), 5);
    }

    @Test
    public void getName() throws Exception {
        assertEquals(tariff.getName(), "OurTariff");
    }

    @Test
    public void setName() throws Exception {
        tariff.setName("tar");
        assertEquals(tariff.getName(), "tar");
    }

    @Test
    public void getPrice() throws Exception {
        assertEquals(tariff.getPrice(), 20.f, 0.0005);
    }

    @Test
    public void setPrice() throws Exception {
        tariff.setPrice(9.6f);
        assertEquals(tariff.getPrice(), 9.6f, 0.0005);
    }

    @Test
    public void getOptions() throws Exception {
        assertTrue(tariff.getOptions().size() == 1);
    }

    @Test
    public void setOptions() throws Exception {
        tariff.setOptions(null);
        assertNull(tariff.getOptions());
    }

    @Test
    public void toStringTest() {
        assertEquals(tariff.toString(), "Tariff{id=60, name='OurTariff', price=20.0, options=[Option{id=2, name='FirstOpt', price=0.0, connectionCost=2.0}]}");
    }

    @Test
    public void equalsHashCodeTest() {
        List<Option> options = Arrays.asList(new Option(2, "FirstOpt", 0.0f, 2.0f, null, null));
        Tariff equalTariff = new Tariff(60, "OurTariff", 20.f, options);

        assertTrue(tariff.equals(equalTariff));
        assertEquals(tariff.hashCode(), equalTariff.hashCode());
    }

    @Test
    public void emptyConstructorTest(){
        Tariff tar = new Tariff();
        assertNull(tar.getName());
        assertEquals(tar.getPrice(), 0.f, 0.0005);
        assertEquals(tar.getId(), 0);
        assertNull(tar.getOptions());
    }

}