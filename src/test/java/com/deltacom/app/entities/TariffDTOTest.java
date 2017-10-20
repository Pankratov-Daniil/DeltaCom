package com.deltacom.app.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TariffDTOTest {
    TariffDTO tariffDTO;

    @Before
    public void setUp() {
        tariffDTO = new TariffDTO(1, "Tariff", 200, new String[]{"1"});
    }
    @Test
    public void getId() throws Exception {
        assertEquals(tariffDTO.getId(), 1);
    }

    @Test
    public void setId() throws Exception {
        tariffDTO.setId(6);
        assertEquals(tariffDTO.getId(), 6);
    }

    @Test
    public void getName() throws Exception {
        assertEquals(tariffDTO.getName(), "Tariff");
    }

    @Test
    public void setName() throws Exception {
        tariffDTO.setName("OK");
        assertEquals(tariffDTO.getName(), "OK");
    }

    @Test
    public void getPrice() throws Exception {
        assertEquals(tariffDTO.getPrice(), 200, 0.0005);
    }

    @Test
    public void setPrice() throws Exception {
        tariffDTO.setPrice(0);
        assertEquals(tariffDTO.getPrice(), 0, 0.0005);
    }

    @Test
    public void getOptionsIds() throws Exception {
        assertArrayEquals(tariffDTO.getOptionsIds(), new String[]{"1"});
    }

    @Test
    public void setOptionsIds() throws Exception {
        tariffDTO.setOptionsIds(null);
        assertNull(tariffDTO.getOptionsIds());
    }

    @Test
    public void equalsHasCodeTest() throws Exception {
        TariffDTO equalDTO = new TariffDTO(1, "Tariff", 200, new String[]{"1"});
        TariffDTO notEqualDTO = new TariffDTO();

        assertEquals(tariffDTO.hashCode(), equalDTO.hashCode());
        assertTrue(tariffDTO.equals(equalDTO));
        assertTrue(tariffDTO.equals(tariffDTO));
        assertFalse(tariffDTO.equals(null));
        assertFalse(tariffDTO.equals(new Integer(2)));
        assertFalse(tariffDTO.equals(notEqualDTO));
        notEqualDTO.setId(1);
        assertFalse(tariffDTO.equals(notEqualDTO));
        notEqualDTO.setName("Tariff");
        assertFalse(tariffDTO.equals(notEqualDTO));
        notEqualDTO.setPrice(200);
        assertFalse(tariffDTO.equals(notEqualDTO));
    }

    @Test
    public void toStringTest() throws Exception {
        assertEquals(tariffDTO.toString(), "TariffDTO{id=1, name='Tariff', price=200.0, optionsIds=[1]}");
    }

}