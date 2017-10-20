package com.deltacom.app.entities;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ContractTest {
    Contract contract;

    @Before
    public void setUp() throws Exception {
        List<Option> options = Arrays.asList(new Option(), new Option(), new Option());
        contract = new Contract(4, new NumbersPool("88", false), false, false, 50, new Tariff(1, "Name", 0, null), new Client(), options);
    }

    @Test
    public void getClient() throws Exception {
        assertEquals(contract.getClient(), new Client());
    }

    @Test
    public void setClient() throws Exception {
        contract.setClient(null);
        assertNull(contract.getClient());
    }

    @Test
    public void getTariff() throws Exception {
        assertEquals(contract.getTariff(), new Tariff(1, "Name", 0, null));
    }

    @Test
    public void setTariff() throws Exception {
        contract.setTariff(null);
        assertNull(contract.getTariff());
    }

    @Test
    public void getId() throws Exception {
        assertEquals(contract.getId(), 4);
    }

    @Test
    public void setId() throws Exception {
        contract.setId(-1);
        assertEquals(contract.getId(), -1);
    }

    @Test
    public void getNumbersPool() throws Exception {
        assertEquals(contract.getNumbersPool(), new NumbersPool("88", false));
    }

    @Test
    public void setNumbersPool() throws Exception {
        contract.setNumbersPool(null);
        assertNull(contract.getNumbersPool());
    }

    @Test
    public void isBlocked() throws Exception {
        assertFalse(contract.isBlocked());
    }

    @Test
    public void setBlocked() throws Exception {
        contract.setBlocked(true);
        assertTrue(contract.isBlocked());
    }

    @Test
    public void isBlockedByOperator() throws Exception {
        assertFalse(contract.isBlockedByOperator());
    }

    @Test
    public void setBlockedByOperator() throws Exception {
        contract.setBlockedByOperator(true);
        assertTrue(contract.isBlockedByOperator());
    }

    @Test
    public void getBalance() throws Exception {
        assertEquals(contract.getBalance(), 50, 0.0005);
    }

    @Test
    public void setBalance() throws Exception {
        contract.setBalance(0);
        assertEquals(contract.getBalance(), 0, 0.0005);
    }

    @Test
    public void getOptions() throws Exception {
        assertTrue(contract.getOptions().size() == 3);
    }

    @Test
    public void setOptions() throws Exception {
        contract.setOptions(null);
        assertNull(contract.getOptions());
    }

    @Test
    public void equalsHashCodeTest() throws Exception {
        List<Option> options = Arrays.asList(new Option(), new Option(), new Option());
        Contract equalContract = new Contract(4, new NumbersPool("88", false), false, false, 50, new Tariff(1, "Name", 0, null), new Client(), options);

        assertTrue(contract.equals(equalContract));
        assertEquals(contract.hashCode(), equalContract.hashCode());
    }

    @Test
    public void toStringTest() {
        assertEquals(contract.toString(), "Contract{id=4, numbersPool=NumbersPool{number='88', used=false}, blocked=false, blockedByOperator=false, balance=50.0, tariff=Tariff{id=1, name='Name', price=0.0, options=null}, options=[Option{id=0, name='null', price=0.0, connectionCost=0.0}, Option{id=0, name='null', price=0.0, connectionCost=0.0}, Option{id=0, name='null', price=0.0, connectionCost=0.0}]}");
    }

    @Test
    public void emptyConstructorTest() {
        Contract emptyContract = new Contract();

        assertEquals(emptyContract.getId(), 0);
        assertNull(emptyContract.getClient());
        assertNull(emptyContract.getOptions());
        assertNull(emptyContract.getNumbersPool());
        assertNull(emptyContract.getTariff());
        assertEquals(emptyContract.getBalance(), 0, 0.0005);
    }

    @Test
    public void notFullConstructorTest() {
        Contract emptyContract = new Contract(new Client(), new NumbersPool(), new Tariff(), new ArrayList<>());

        assertEquals(emptyContract.getId(), 0);
        assertNotNull(emptyContract.getClient());
        assertNotNull(emptyContract.getOptions());
        assertNotNull(emptyContract.getNumbersPool());
        assertNotNull(emptyContract.getTariff());
        assertEquals(emptyContract.getBalance(), 0, 0.0005);
    }

}