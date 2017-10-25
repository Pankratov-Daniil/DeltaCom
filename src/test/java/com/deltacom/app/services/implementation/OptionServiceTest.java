package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.Option;
import com.deltacom.app.exceptions.RepositoryException;
import com.deltacom.app.services.api.OptionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-test.xml")
public class OptionServiceTest {
    @Autowired
    private OptionService optionService;

    @Test
    public void getOptionById() throws Exception {
        assertEquals(optionService.getOptionById(1).getName(), "Internet");
        assertEquals(optionService.getOptionById(1564), null);
    }

    @Test
    public void getAllOptionsForTariff() throws Exception {
        assertEquals(optionService.getAllOptionsForTariff(1).size(), 3);
        assertEquals(optionService.getAllOptionsForTariff(213).size(), 0);
    }

    @Test
    public void getAllOptions() throws Exception {
        assertEquals(optionService.getAllOptions().size(), 6);
    }

    @Test
    @Rollback
    public void updateOption() throws Exception {
        String[] incompatibleOptionsIds = new String[]{"1","2"};
        String[] compatibleOptionsIds = new String[]{"3"};
        optionService.updateOption(new Option(1, "InterNOT", 500, 500, new ArrayList<>(), new ArrayList<>()), incompatibleOptionsIds, compatibleOptionsIds);
        assertEquals(optionService.getOptionById(1).getName(), "InterNOT");
    }

    @Test
    @Rollback
    public void addOption() throws Exception {
        String[] incompatibleOptionsIds = new String[]{"1","2"};
        String[] compatibleOptionsIds = new String[]{"3"};
        Option optionToAdd = new Option(0, "Ooooption", 900, 300, null, null);
        optionService.addOption(optionToAdd,  incompatibleOptionsIds, compatibleOptionsIds);
        assertEquals(optionService.getAllOptions().size(), 7);
    }

    @Test(expected = RepositoryException.class)
    @Rollback
    public void addOptionExceptionTest() {
        Option optionToAdd = new Option(0, "Internet", 900, 300, null, null);
        String[] incompatibleOptionsIds = new String[]{"1","3"};
        String[] compatibleOptionsIds = new String[] {"3", "4"};
        optionService.addOption(optionToAdd,  incompatibleOptionsIds, compatibleOptionsIds);
    }

    @Test
    @Rollback
    public void deleteOption() throws Exception {
        optionService.deleteOption(1);
        assertEquals(optionService.getAllOptions().size(), 5);
        assertEquals(optionService.getOptionById(1), null);
    }
}