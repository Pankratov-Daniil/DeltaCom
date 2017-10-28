package com.deltacom.app.exceptions;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.persistence.PersistenceException;

public class ContractExceptionTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void throwExceptionTest() {
        expectedException.expect(ContractException.class);
        expectedException.expectMessage("Test ContractException");

        throw new ContractException("Test ContractException", new PersistenceException());
    }
}