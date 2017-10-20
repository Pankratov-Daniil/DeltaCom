package com.deltacom.app.services.implementation;

import com.deltacom.app.services.api.AccessLevelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-test.xml")
public class AccessLevelServiceTest {
    @Autowired
    AccessLevelService accessLevelService;
    @PersistenceContext
    EntityManager em;

    @Test
    public void getAllAccessLevels() {
        assertEquals(accessLevelService.getAllAccessLevels().size(), 3);
    }
}