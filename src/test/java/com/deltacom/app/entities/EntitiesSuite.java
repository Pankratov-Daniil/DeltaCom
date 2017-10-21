package com.deltacom.app.entities;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({AccessLevelTest.class, ClientDTOTest.class, ClientTest.class, ClientCartTest.class,
        ContractTest.class, NumbersPoolTest.class, OptionDTOTest.class, OptionTest.class, TariffDTOTest.class, TariffTest.class} )
public class EntitiesSuite {
}
