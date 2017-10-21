package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.NumbersPool;
import com.deltacom.app.exceptions.RepositoryException;
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
    private NumbersPoolRepositoryImpl numbersPoolRepository;

    @Test
    public void getAllUnusedNumbers() throws Exception {
        List<String> numbersPools = numbersPoolRepository.getAllUnusedNumbers();

        assertNotNull(numbersPools);
        assertEquals(numbersPools.size(), 1);
        assertTrue(numbersPools.get(0).equals("89314523412"));
    }

    @Test
    @Rollback
    public void addTest() {
        numbersPoolRepository.add(new NumbersPool("9804314", false));
        assertEquals(numbersPoolRepository.getAll().size(), 6);
    }

    @Test(expected = RepositoryException.class)
    @Rollback
    public void addExceptionTest() {
        numbersPoolRepository.add(new NumbersPool("89222222222", true));
        numbersPoolRepository.add(new NumbersPool("89222222222", true));
    }

    @Test
    @Rollback
    public void updateTest() {
        numbersPoolRepository.update(new NumbersPool("89222222222", false));
        assertEquals(numbersPoolRepository.getById("89222222222").isUsed(), false);
    }

    @Test
    @Rollback
    public void removeTest() {
        numbersPoolRepository.remove(new NumbersPool("89222222222", true));
        assertEquals(numbersPoolRepository.getById("89222222222"), null);
    }

    @Test
    public void getByIdTest() {
        assertEquals(numbersPoolRepository.getById("89222222222").isUsed(), true);
    }

    @Test
    public void getAllTest() {
        assertEquals(numbersPoolRepository.getAll().size(), 5);
    }
}