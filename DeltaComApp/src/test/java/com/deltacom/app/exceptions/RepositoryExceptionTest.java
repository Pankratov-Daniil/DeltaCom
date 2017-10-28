package com.deltacom.app.exceptions;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.persistence.PersistenceException;

public class RepositoryExceptionTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void throwExceptionTest() {
        expectedException.expect(RepositoryException.class);
        expectedException.expectMessage("Test RepositoryException");

        throw new RepositoryException("Test RepositoryException", new PersistenceException());
    }
}