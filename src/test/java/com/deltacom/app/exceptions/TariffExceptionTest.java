package com.deltacom.app.exceptions;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.persistence.PersistenceException;

import static org.junit.Assert.*;

public class TariffExceptionTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void throwExceptionTest() {
        expectedException.expect(TariffException.class);
        expectedException.expectMessage("Test TariffException");

        throw new TariffException("Test TariffException", new PersistenceException());
    }
}