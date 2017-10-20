package com.deltacom.app.exceptions;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.persistence.PersistenceException;

public class ClientExceptionTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void throwExceptionTest() {
        expectedException.expect(ClientException.class);
        expectedException.expectMessage("Test ClientException");

        throw new ClientException("Test ClientException", new PersistenceException());
    }
}