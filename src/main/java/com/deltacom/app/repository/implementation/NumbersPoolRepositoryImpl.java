package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.NumbersPool;
import com.deltacom.app.repository.api.NumbersPoolRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

/**
 * Numbers pool repository implementation
 */
@Repository("NumbersPool")
public class NumbersPoolRepositoryImpl extends HibernateRepository<NumbersPool, String> implements NumbersPoolRepository {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Gets all unused numbers
     * @return list of all unused numbers
     */
    @Override
    public List<String> getAllUnusedNumbers() {
        try {
            return (List<String>) entityManager.createQuery("select numbers.number from NumbersPool numbers where numbers.used = false")
                    .getResultList();
        } catch (PersistenceException e) {
            return new ArrayList<>();
        }
    }
}
