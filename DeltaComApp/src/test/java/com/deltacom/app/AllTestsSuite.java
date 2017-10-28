package com.deltacom.app;

import com.deltacom.app.controllers.ControllersSuite;
import com.deltacom.app.entities.EntitiesSuite;
import com.deltacom.app.exceptions.ExceptionsSuite;
import com.deltacom.app.repository.implementation.RepositoriesSuite;
import com.deltacom.app.services.implementation.ServicesSuite;
import com.deltacom.app.utils.UtilsSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({EntitiesSuite.class, ExceptionsSuite.class, UtilsSuite.class, RepositoriesSuite.class,
        ServicesSuite.class, ControllersSuite.class})
public class AllTestsSuite {
}
