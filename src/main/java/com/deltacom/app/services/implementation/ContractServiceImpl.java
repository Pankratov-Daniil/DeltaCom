package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.Contract;
import com.deltacom.app.repository.implementation.ContractSqlRepository;
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
    private ContractSqlRepository contractSqlRepository;

    /**
     * Creates new Contract entity in database.
     * @param entity Contract entity to be created
     */
    @Override
    @Transactional
    public void createEntity(final Contract entity) {
        contractSqlRepository.add(entity);
    }

    /**
     * Updates Contract entity in database.
     * @param entity Contract entity to be updated
     */
    @Override
    @Transactional
    public void updateEntity(final Contract entity) {
        contractSqlRepository.update(entity);
    }

    /**
     * Deletes Contract entity in database.
     * @param entity Contract entity to be deleted
     */
    @Override
    @Transactional
    public void deleteEntity(final Contract entity) {
        contractSqlRepository.remove(entity);
    }

    /**
     * Gets Contract entity by its id from database.
     * @param id id of Contract entity to be found
     * @return founded Contract entity
     */
    @Override
    @Transactional
    public Contract getEntityById(final int id) {
        return (Contract) contractSqlRepository.getById(id);
    }

    /**
     * Gets all Contract entities from database.
     * @return List of Contract entities from database
     */
    @Override
    @Transactional
    public List<Contract> getAllEntities() {
        return contractSqlRepository.getAll();
    }
}
