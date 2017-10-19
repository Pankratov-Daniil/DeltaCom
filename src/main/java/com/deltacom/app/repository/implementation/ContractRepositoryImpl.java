package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.Contract;
import com.deltacom.app.entities.Tariff;
import com.deltacom.app.repository.api.ContractRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

/**
 * Contract repository implementation
 */
@Repository("Contract")
public class ContractRepositoryImpl extends HibernateRepository<Contract, Integer> implements ContractRepository {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Gets all contracts by client id.
     * @param clientId client id
     * @return list of contracts for client or null if nothing found
     */
    @Override
    public List<Contract> getAllClientContractsById(int clientId) {
        try {
            return (List<Contract>) entityManager.createQuery("select contract from Contract contract where contract.client.id = :id")
                    .setParameter("id", clientId).getResultList();
        } catch (PersistenceException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Gets contract by number
     * @param number number of contract
     * @return found contract or null if exception was thrown
     */
    @Override
    public Contract getContractByNumber(String number) {
        try {
            return (Contract) entityManager.createQuery("select contract from Contract contract where contract.numbersPool.number = :number")
                    .setParameter("number", number).getSingleResult();
        } catch (PersistenceException e) {
            return null;
        }
    }

    /**
     * Gets contract by tariff
     * @param tariff tariff of contract
     * @return list of contracts
     */
    @Override
    public List<Contract> getAllContractsByTariff(Tariff tariff) {
        try {
            return (List<Contract>) entityManager.createQuery("select contract from Contract contract where contract.tariff = :tariff")
                    .setParameter("tariff", tariff).getResultList();
        } catch (PersistenceException e) {
            return new ArrayList<>();
        }
    }
}
