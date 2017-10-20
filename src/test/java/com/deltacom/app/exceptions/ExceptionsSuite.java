package com.deltacom.app.exceptions;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({AccessLevelExceptionTest.class, ClientExceptionTest.class,
        ContractExceptionTest.class, NumbersPoolExceptionTest.class, OptionExceptionTest.class,
        RepositoryExceptionTest.class, TariffExceptionTest.class})
public class ExceptionsSuite {
}
