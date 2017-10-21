package com.deltacom.app.entities;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

public class NumbersPoolTest {
    private NumbersPool numbersPool;

    @Before
    public void setup() {
        numbersPool = new NumbersPool("8931222", true);
    }

    @Test
    public void getNumber() throws Exception {
        assertEquals(numbersPool.getNumber(), "8931222");
    }

    @Test
    public void setNumber() throws Exception {
        numbersPool.setNumber("000");
        assertEquals(numbersPool.getNumber(), "000");
    }

    @Test
    public void isUsed() throws Exception {
        assertTrue(numbersPool.isUsed());
    }

    @Test
    public void setUsed() throws Exception {
        numbersPool.setUsed(false);
        assertFalse(numbersPool.isUsed());
    }

    @Test
    public void equalsHashCodeTest() throws Exception {
        NumbersPool newNumbPool = new NumbersPool("8931222", true);
        NumbersPool notEqualPool = new NumbersPool(null, true);

        assertTrue(numbersPool.equals(newNumbPool));
        assertEquals(numbersPool.hashCode(), newNumbPool.hashCode());
        assertTrue(numbersPool.equals(numbersPool));
        assertFalse(numbersPool.equals(null));
        assertFalse(numbersPool.equals(new Integer(2)));

        newNumbPool.setUsed(false);
        numbersPool.setUsed(false);
        assertEquals(numbersPool.hashCode(), newNumbPool.hashCode());

        assertNotEquals(numbersPool.hashCode(), notEqualPool.hashCode());
        assertFalse(numbersPool.equals(notEqualPool));
        notEqualPool.setNumber("89");
        assertFalse(numbersPool.equals(notEqualPool));
        notEqualPool.setNumber("8931222");
        assertFalse(numbersPool.equals(notEqualPool));
    }

    @Test
    public void toStringTest() throws Exception {
        assertEquals(numbersPool.toString(), "NumbersPool{number='8931222', used=true}");
    }

    @Test
    public void emptyConstructorTest() {
        NumbersPool numbersPool = new NumbersPool();
        assertEquals(numbersPool.getNumber(), null);
        assertEquals(numbersPool.isUsed(), false);
    }

}