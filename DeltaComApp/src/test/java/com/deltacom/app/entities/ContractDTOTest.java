package com.deltacom.app.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContractDTOTest {
    private ContractDTO contractDTO;

    @Before
    public void setUp() throws Exception {
        contractDTO = new ContractDTO(9, "8922", 1, new int[]{1, 2});
    }

    @Test
    public void getClientId() {
        assertEquals(contractDTO.getClientId(), 9);
    }

    @Test
    public void setClientId() {
        contractDTO.setClientId(0);
        assertEquals(contractDTO.getClientId(), 0);
    }

    @Test
    public void getNumber() throws Exception {
        assertEquals(contractDTO.getNumber(), "8922");
    }

    @Test
    public void setNumber() throws Exception {
        contractDTO.setNumber("8");
        assertEquals(contractDTO.getNumber(), "8");
    }

    @Test
    public void getTariffId() throws Exception {
        assertEquals(contractDTO.getTariffId(), 1);
    }

    @Test
    public void setTariffId() throws Exception {
        contractDTO.setTariffId(55);
        assertEquals(contractDTO.getTariffId(), 55);
    }

    @Test
    public void getOptionsIds() throws Exception {
        assertArrayEquals(contractDTO.getOptionsIds(), new int[]{1,2});
    }

    @Test
    public void setOptionsIds() throws Exception {
        contractDTO.setOptionsIds(null);
        assertEquals(contractDTO.getOptionsIds(), null);
    }

    @Test
    public void toStringTest() throws Exception {
        assertEquals(contractDTO.toString(), "ContractDTO{clientId=9, number='8922', tariffId=1, optionsIds=[1, 2]}");
    }

}