package com.deltacom.app.services.implementation;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({AccessLevelServiceTest.class, ClientServiceTest.class, ContractServiceTest.class,
        NumbersPoolServiceTest.class, OptionServiceTest.class, TariffServiceTest.class})
public class ServicesSuite {
}
