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

public class OptionTest {
    Option option;

    @Before
    public void setUp() throws Exception {
        List<Option> compatibleOpts = Arrays.asList(new Option(2, "FirstOpt", 0.0f, 2.0f, null, null),
                new Option(3, "Second", 3.0f, 4.0f, null, null));
        List<Option> incompatibleOpts = Arrays.asList(new Option(4, "Third", 6.0f, 7.0f, null, null),
                new Option(6, "Another", 8.0f, 9.0f, null, null));

        option = new Option(1, "OptionName", 200.0f, 100.0f, compatibleOpts, incompatibleOpts);
    }

    @Test
    public void getId() throws Exception {
        assertEquals(option.getId(), 1);
    }

    @Test
    public void setId() throws Exception {
        option.setId(-1);
        assertEquals(option.getId(), -1);
    }

    @Test
    public void getName() throws Exception {
        assertEquals(option.getName(), "OptionName");
    }

    @Test
    public void setName() throws Exception {
        option.setName("Opt");
        assertEquals(option.getName(), "Opt");
    }

    @Test
    public void getPrice() throws Exception {
        assertEquals(option.getPrice(), 200.0f, 0.0005);
    }

    @Test
    public void setPrice() throws Exception {
        option.setPrice(-2.0f);
        assertEquals(option.getPrice(), -2.0f, 0.0005);
    }

    @Test
    public void getConnectionCost() throws Exception {
        assertEquals(option.getConnectionCost(), 100.f, 0.0005);
    }

    @Test
    public void setConnectionCost() throws Exception {
        option.setConnectionCost(40.f);
        assertEquals(option.getConnectionCost(), 40.f, 0.0005);
    }

    @Test
    public void getIncompatibleOptions() throws Exception {
        assertTrue(option.getIncompatibleOptions().size() == 2);
    }

    @Test
    public void setIncompatibleOptions() throws Exception {
        option.setIncompatibleOptions(null);
        assertNull(option.getIncompatibleOptions());
    }

    @Test
    public void getCompatibleOptions() throws Exception {
        assertTrue(option.getCompatibleOptions().size() == 2);
    }

    @Test
    public void setCompatibleOptions() throws Exception {
        option.setCompatibleOptions(null);
        assertNull(option.getCompatibleOptions());
    }

    @Test
    public void toStringTest() throws Exception {
        assertEquals(option.toString(), "Option{id=1, name='OptionName', price=200.0, connectionCost=100.0}");
    }

    @Test
    public void equalsHashCodeTest() throws Exception {
        List<Option> compatibleOpts = Arrays.asList(new Option(2, "FirstOpt", 0.0f, 2.0f, null, null),
                new Option(3, "Second", 3.0f, 4.0f, null, null));
        List<Option> incompatibleOpts = Arrays.asList(new Option(4, "Third", 6.0f, 7.0f, null, null),
                new Option(6, "Another", 8.0f, 9.0f, null, null));

        Option equalOption = new Option(1, "OptionName", 200.0f, 100.0f, compatibleOpts, incompatibleOpts);

        assertTrue(option.equals(equalOption));
        assertEquals(option.hashCode(), equalOption.hashCode());
    }

    @Test
    public void emptyConstructorTest() {
        Option emptyOption = new Option();

        assertEquals(emptyOption.getId(), 0);
        assertEquals(emptyOption.getConnectionCost(), 0, 0.0005);
        assertEquals(emptyOption.getPrice(), 0, 0.0005);
        assertEquals(emptyOption.getName(), null);
        assertEquals(emptyOption.getCompatibleOptions(), null);
        assertEquals(emptyOption.getIncompatibleOptions(), null);
    }

}