package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.NumbersPool;
import com.deltacom.app.repository.api.NumbersPoolRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Numbers pool repository implementation
 */
@Repository("NumbersPool")
public class NumbersPoolRepositoryImpl extends HibernateRepository<NumbersPool> implements NumbersPoolRepository {
    @PersistenceContext
    private EntityManager entityManager;
}
