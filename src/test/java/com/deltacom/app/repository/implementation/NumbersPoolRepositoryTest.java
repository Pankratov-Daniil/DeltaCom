package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.NumbersPool;
import com.deltacom.app.repository.api.NumbersPoolRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-test.xml")
public class NumbersPoolRepositoryTest {
    @Autowired
    NumbersPoolRepository numbersPoolRepository;

    @Test
    public void getAllUnusedNumbers() throws Exception {
        List<String> numbersPools = numbersPoolRepository.getAllUnusedNumbers();

        assertNotNull(numbersPools);
        assertEquals(numbersPools.size(), 1);
        assertTrue(numbersPools.get(0).equals("89314523412"));
    }
}