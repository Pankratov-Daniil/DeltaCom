package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.Contract;
import com.deltacom.app.repository.api.ContractRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("Contract")
public class ContractRepositoryImpl extends HibernateRepository<Contract> implements ContractRepository {
    @PersistenceContext
    private EntityManager entityManager;
}
