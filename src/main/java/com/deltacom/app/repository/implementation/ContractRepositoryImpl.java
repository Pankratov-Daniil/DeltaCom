package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.Client;
import com.deltacom.app.entities.Contract;
import com.deltacom.app.repository.api.ContractRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("Contract")
public class ContractRepositoryImpl extends HibernateRepository<Contract> implements ContractRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Contract> getAllClientContractsById(int clientId) {
        try {
            return (List<Contract>) entityManager.createQuery("select contract from Contract contract where contract.client.id = :id")
                    .setParameter("id", clientId).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
