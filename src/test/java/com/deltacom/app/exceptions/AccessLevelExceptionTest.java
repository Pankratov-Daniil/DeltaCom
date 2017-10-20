package com.deltacom.app.exceptions;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.persistence.PersistenceException;

public class AccessLevelExceptionTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void throwExceptionTest() {
        expectedException.expect(AccessLevelException.class);
        expectedException.expectMessage("Test ALException");

        throw new AccessLevelException("Test ALException", new PersistenceException());
    }
}