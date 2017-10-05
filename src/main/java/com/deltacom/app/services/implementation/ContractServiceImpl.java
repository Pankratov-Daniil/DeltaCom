package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.Contract;
import com.deltacom.app.repository.implementation.ContractRepositoryImpl;
import com.deltacom.app.services.api.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Operations with repository for Contract entities.
 */
@Service("ContractService")
public class ContractServiceImpl implements ContractService {
    @Autowired
    private ContractRepositoryImpl contractRepository;

    /**
     * Creates new Contract entity in database.
     * @param entity Contract entity to be created
     */
    @Override
    @Transactional
    public void create(Contract entity) {
        contractRepository.add(entity);
    }

    /**
     * Updates Contract entity in database.
     * @param entity Contract entity to be updated
     */
    @Override
    @Transactional
    public void update(Contract entity) {
        contractRepository.update(entity);
    }

    /**
     * Deletes Contract entity in database.
     * @param entity Contract entity to be deleted
     */
    @Override
    @Transactional
    public void delete(Contract entity) {
        contractRepository.remove(entity);
    }

    /**
     * Gets Contract entity by its id from database.
     * @param id id of Contract entity to be found
     * @return founded Contract entity
     */
    @Override
    @Transactional
    public Contract getById(int id) {
        return (Contract) contractRepository.getById(id);
    }

    /**
     * Gets all Contract entities from database.
     * @return List of Contract entities from database
     */
    @Override
    @Transactional
    public List<Contract> getAll() {
        return contractRepository.getAll();
    }

    @Override
    public List<Contract> getAllClientContractsById(int clientId) {
        return contractRepository.getAllClientContractsById(clientId);
    }
}
