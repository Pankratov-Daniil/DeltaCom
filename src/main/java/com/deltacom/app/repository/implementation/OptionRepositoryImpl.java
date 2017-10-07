package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.Option;
import com.deltacom.app.repository.api.OptionRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Option repository implementation
 */
@Repository("Option")
public class OptionRepositoryImpl extends HibernateRepository<Option, Integer> implements OptionRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Option> getAllOptionsForTariff(int id) {
        try {
            return (List<Option>) entityManager.createQuery(
                    "select tariff.options from Tariff tariff where tariff.id  = :id")
                    .setParameter("id", id)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
