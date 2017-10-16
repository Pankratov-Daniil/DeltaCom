package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.Option;
import com.deltacom.app.repository.api.OptionRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Option repository implementation
 */
@Repository("Option")
public class OptionRepositoryImpl extends HibernateRepository<Option, Integer> implements OptionRepository {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Gets all option for tariff by its id
     * @param id tariff id
     * @return list of all available options for tariff
     */
    @Override
    public List<Option> getAllOptionsForTariff(int id) {
        try {
            return (List<Option>) entityManager.createQuery(
                    "select tariff.options from Tariff tariff where tariff.id  = :id")
                    .setParameter("id", id)
                    .getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }
}
