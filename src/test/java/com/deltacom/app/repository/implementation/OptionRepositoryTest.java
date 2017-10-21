package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.Option;
import com.deltacom.app.exceptions.RepositoryException;
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
    private OptionRepositoryImpl optionRepository;

    @Test
    public void getAllOptionsForTariff() throws Exception {
        List<Option> realOptions = optionRepository.getAllOptionsForTariff(2);
        List<Option> noneOptions = optionRepository.getAllOptionsForTariff(987);

        assertNotNull(realOptions);
        assertTrue(realOptions.size() == 3);
        assertTrue(noneOptions.isEmpty());
    }

    @Test
    @Rollback
    public void addTest() {
        Option option = new Option(0, "Opt", 10, 20, null, null);
        optionRepository.add(option);

        assertEquals(optionRepository.getAll().size(), 7);
    }

    @Test(expected = RepositoryException.class)
    @Rollback
    public void addExceptionTest() {
        Option option = new Option(1, "Opt", 10, 20, null, null);
        optionRepository.add(option);
    }

    @Test
    @Rollback
    public void updateTest() {
        Option option = new Option(1, "InterNOT", 500, 500, null, null);
        optionRepository.update(option);
        assertEquals(optionRepository.getById(1).getName(), "InterNOT");
    }

    @Test
    @Rollback
    public void removeTest() {
        Option option = new Option(1, "Internet", 500, 500, null, null);
        optionRepository.remove(option);
        assertEquals(optionRepository.getById(1), null);
    }

    @Test
    public void getByIdTest() {
        assertEquals(optionRepository.getById(1).getName(), "Internet");
    }

    @Test
    public void getAllTest() {
        assertEquals(optionRepository.getAll().size(), 6);
    }
}