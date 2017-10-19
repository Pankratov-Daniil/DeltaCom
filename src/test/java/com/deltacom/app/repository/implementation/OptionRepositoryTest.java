package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.Option;
import com.deltacom.app.repository.api.OptionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-test.xml")
public class OptionRepositoryTest {
    @Autowired
    OptionRepositoryImpl optionRepository;

    @Test
    public void getAllOptionsForTariff() throws Exception {
        List<Option> realOptions = optionRepository.getAllOptionsForTariff(2);
        List<Option> noneOptions = optionRepository.getAllOptionsForTariff(987);

        assertNotNull(realOptions);
        assertTrue(realOptions.size() == 3);
        assertTrue(noneOptions.isEmpty());
    }
}