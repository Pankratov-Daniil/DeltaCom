package com.deltacom.app.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OptionDTOTest {
    private OptionDTO optionDTO;

    @Before
    public void setUp() throws Exception {
        optionDTO = new OptionDTO(1, "Name", 200, 500, new String[0], new String[]{"2"});
    }

    @Test
    public void getId() throws Exception {
        assertEquals(optionDTO.getId(), 1);
    }

    @Test
    public void setId() throws Exception {
        optionDTO.setId(4);
        assertEquals(optionDTO.getId(), 4);
    }

    @Test
    public void getName() throws Exception {
        assertEquals(optionDTO.getName(), "Name");
    }

    @Test
    public void setName() throws Exception {
        optionDTO.setName("NewName");
        assertEquals(optionDTO.getName(), "NewName");
    }

    @Test
    public void getPrice() throws Exception {
        assertEquals(optionDTO.getPrice(), 200, 0.0005);
    }

    @Test
    public void setPrice() throws Exception {
        optionDTO.setPrice(65);
        assertEquals(optionDTO.getPrice(), 65, 0.0005);
    }

    @Test
    public void getConnectionCost() throws Exception {
        assertEquals(optionDTO.getConnectionCost(), 500, 0.0005);
    }

    @Test
    public void setConnectionCost() throws Exception {
        optionDTO.setConnectionCost(2);
        assertEquals(optionDTO.getConnectionCost(), 2, 0.0005);
    }

    @Test
    public void getIncompatibleOptions() throws Exception {
        assertArrayEquals(optionDTO.getIncompatibleOptions(), new String[0]);
    }

    @Test
    public void setIncompatibleOptions() throws Exception {
        optionDTO.setIncompatibleOptions(null);
        assertArrayEquals(optionDTO.getIncompatibleOptions(), null);
    }

    @Test
    public void getCompatibleOptions() throws Exception {
        assertArrayEquals(optionDTO.getCompatibleOptions(), new String[]{"2"});
    }

    @Test
    public void setCompatibleOptions() throws Exception {
        optionDTO.setCompatibleOptions(null);
        assertArrayEquals(optionDTO.getCompatibleOptions(), null);
    }

    @Test
    public void equalsHashCodeTest() throws Exception {
        OptionDTO equalOptionDTO = new OptionDTO(1, "Name", 200, 500, new String[0], new String[]{"2"});
        OptionDTO notEqualOptionDTO = new OptionDTO();

        assertTrue(optionDTO.equals(equalOptionDTO));
        assertTrue(optionDTO.equals(optionDTO));
        assertFalse(optionDTO.equals(null));
        assertFalse(optionDTO.equals(new Integer(2)));
        assertEquals(optionDTO.hashCode(), equalOptionDTO.hashCode());
        assertFalse(optionDTO.equals(notEqualOptionDTO));
        notEqualOptionDTO.setId(1);
        assertFalse(optionDTO.equals(notEqualOptionDTO));
        notEqualOptionDTO.setName("Name");
        assertFalse(optionDTO.equals(notEqualOptionDTO));
        notEqualOptionDTO.setPrice(200);
        assertFalse(optionDTO.equals(notEqualOptionDTO));
        notEqualOptionDTO.setConnectionCost(500);
        assertFalse(optionDTO.equals(notEqualOptionDTO));
        notEqualOptionDTO.setIncompatibleOptions(new String[]{"5"});
        assertFalse(optionDTO.equals(notEqualOptionDTO));
        notEqualOptionDTO.setIncompatibleOptions(new String[0]);
        notEqualOptionDTO.setCompatibleOptions(new String[0]);
        assertFalse(optionDTO.equals(notEqualOptionDTO));
    }

    @Test
    public void toStringTest() throws Exception {
        assertEquals(optionDTO.toString(), "OptionDTO{id=1, name='Name', price=200.0, connectionCost=500.0, incompatibleOptions=[], compatibleOptions=[2]}");
    }

    @Test
    public void toOptionTest() {
        assertEquals(optionDTO.toOption(), new Option(1, "Name", 200, 500, null, null));
    }

}