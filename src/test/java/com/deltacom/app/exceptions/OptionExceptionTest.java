package com.deltacom.app.exceptions;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.persistence.PersistenceException;

public class OptionExceptionTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void throwExceptionTest() {
        expectedException.expect(OptionException.class);
        expectedException.expectMessage("Test OptionException");

        throw new OptionException("Test OptionException", new PersistenceException());
    }
}