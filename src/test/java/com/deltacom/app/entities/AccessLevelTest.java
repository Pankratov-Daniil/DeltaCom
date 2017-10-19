package com.deltacom.app.entities;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-test.xml")
public class AccessLevelTest {
    AccessLevel accessLevel;

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
        assertTrue(accessLevel.equals(anotherAccessLevel));
        assertEquals(accessLevel.hashCode(), anotherAccessLevel.hashCode());
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