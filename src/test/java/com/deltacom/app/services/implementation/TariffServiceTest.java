package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.Tariff;
import com.deltacom.app.exceptions.RepositoryException;
import com.deltacom.app.exceptions.TariffException;
import com.deltacom.app.services.api.TariffService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-test.xml")
public class TariffServiceTest {
    @Autowired
    private TariffService tariffService;

    @Test
    public void getTariffById() throws Exception {
        assertEquals(tariffService.getTariffById(2).getName(), "Tariff2");
    }

    @Test
    public void getAllTariffs() throws Exception {
        assertEquals(tariffService.getAllTariffs().size(), 3);
    }

    @Test
    @Rollback
    public void addTariff() throws Exception {
        tariffService.addTariff(new Tariff(), new String[]{"1","2"});
        assertEquals(tariffService.getAllTariffs().size(), 4);
    }

    @Test(expected = RepositoryException.class)
    @Rollback
    public void addTariffException() throws Exception {
        tariffService.addTariff(new Tariff(), new String[0]);
        tariffService.addTariff(new Tariff(), new String[0]);
    }

    @Test
    @Rollback
    public void updateTariff() throws Exception {
        tariffService.updateTariff(new Tariff(1, "MyNewTariffName", 200, null), new String[0]);

        assertEquals(tariffService.getTariffById(1).getName(), "MyNewTariffName");
    }

    @Test
    @Rollback
    public void deleteTariff() throws Exception {
        tariffService.deleteTariff(2);
        assertEquals(tariffService.getAllTariffs().size(), 2);
    }

    @Test(expected = TariffException.class)
    @Rollback
    public void deleteTariffExceptionTest() {
        tariffService.deleteTariff(95);
    }
}