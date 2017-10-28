package com.deltacom.app.entities;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

public class AccessLevelTest {
    private AccessLevel accessLevel;

    @Before
    public void setUp() throws Exception {
        accessLevel = new AccessLevel(19, "MegaUser");
    }

    @Test
    public void getId() throws Exception {
        assertEquals(accessLevel.getId(), 19);
    }

    @Test
    public void setId() throws Exception {
        accessLevel.setId(20);
        assertEquals(accessLevel.getId(), 20);
    }

    @Test
    public void getName() throws Exception {
        assertEquals(accessLevel.getName(), "MegaUser");
    }

    @Test
    public void setName() throws Exception {
        accessLevel.setName("NotMegaUser");
        assertEquals(accessLevel.getName(), "NotMegaUser");
    }

    @Test
    public void toStringTest() throws Exception {
        assertEquals(accessLevel.toString(), "AccessLevel{id=19, name='MegaUser'}");
    }

    @Test
    public void equalsHashCodeTest() throws Exception {
        AccessLevel anotherAccessLevel = new AccessLevel(19, "MegaUser");
        AccessLevel notEqualAccessLevel = new AccessLevel(19, null);

        assertTrue(accessLevel.equals(anotherAccessLevel));
        assertEquals(accessLevel.hashCode(), anotherAccessLevel.hashCode());
        assertNotEquals(accessLevel.hashCode(), notEqualAccessLevel.hashCode());
        assertTrue(accessLevel.equals(accessLevel));
        assertFalse(accessLevel.equals(null));
        assertFalse(accessLevel.equals(new Integer(2)));
        notEqualAccessLevel.setId(0);
        assertFalse(accessLevel.equals(notEqualAccessLevel));
        notEqualAccessLevel.setId(19);
        assertFalse(accessLevel.equals(notEqualAccessLevel));
        assertFalse(notEqualAccessLevel.equals(anotherAccessLevel));
        anotherAccessLevel.setName(null);
        assertTrue(notEqualAccessLevel.equals(anotherAccessLevel));
    }

    @Test
    public void emptyConstructorTest() {
        AccessLevel emptyAccessLevel = new AccessLevel();
        assertEquals(emptyAccessLevel.getName(), null);
        assertEquals(emptyAccessLevel.getId(), 0);
    }

    @Test
    public void constructorWithIdTest() {
        AccessLevel accessLevelWithId = new AccessLevel(256);
        assertEquals(accessLevelWithId.getId(), 256);
        assertEquals(accessLevelWithId.getName(), null);
    }
}