package com.deltacom.app.exceptions;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.persistence.PersistenceException;

public class NumbersPoolExceptionTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void throwExceptionTest() {
        expectedException.expect(NumbersPoolException.class);
        expectedException.expectMessage("Test NumbersPoolException");

        throw new NumbersPoolException("Test NumbersPoolException", new PersistenceException());
    }
}