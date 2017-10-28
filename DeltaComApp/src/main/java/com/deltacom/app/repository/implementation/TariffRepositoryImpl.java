package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.Tariff;
import com.deltacom.app.repository.api.TariffRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Tariff repository implementation
 */
@Repository("Tariff")
public class TariffRepositoryImpl extends HibernateRepository<Tariff, Integer> implements TariffRepository {
    @PersistenceContext
    private EntityManager entityManager;
}
