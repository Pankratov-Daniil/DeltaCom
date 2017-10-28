package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.NumbersPool;
import com.deltacom.app.repository.implementation.NumbersPoolRepositoryImpl;
import com.deltacom.app.services.api.NumbersPoolService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-test.xml")
public class NumbersPoolServiceTest {
    @Autowired
    private NumbersPoolService numbersPoolService;
    @Autowired
    private NumbersPoolRepositoryImpl numbersPoolRepository;

    @Test
    @Rollback
    public void updateNumbersPool() throws Exception {
        numbersPoolService.updateNumbersPool(new NumbersPool("89222222222", false));
        assertEquals(numbersPoolRepository.getById("89222222222").isUsed(), false);
    }

    @Test
    public void getAllUnusedNumbers() throws Exception {
        assertEquals(numbersPoolService.getAllUnusedNumbers().size(), 1);
    }

}