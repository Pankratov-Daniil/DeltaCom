package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.Option;
import com.deltacom.app.repository.api.OptionRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("Option")
public class OptionRepositoryImpl extends HibernateRepository<Option> implements OptionRepository {
    @PersistenceContext
    private EntityManager entityManager;
}
