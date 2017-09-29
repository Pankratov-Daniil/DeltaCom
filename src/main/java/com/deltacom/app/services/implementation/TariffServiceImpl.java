package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.Tariff;
import com.deltacom.app.repository.implementation.TariffSqlRepository;
import com.deltacom.app.services.api.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Operations with repository for Tariff entities.
 */
@Service("TariffService")
public class TariffServiceImpl implements TariffService {
    @Autowired
    private TariffSqlRepository tariffSqlRepository;

    /**
     * Creates new Tariff entity in database.
     * @param entity Tariff entity to be created
     */
    @Override
    @Transactional
    public void createEntity(final Tariff entity) {
        tariffSqlRepository.add(entity);
    }

    /**
     * Updates Tariff entity in database.
     * @param entity Tariff entity to be updated
     */
    @Override
    @Transactional
    public void updateEntity(final Tariff entity) {
        tariffSqlRepository.update(entity);
    }

    /**
     * Deletes Tariff entity in database.
     * @param entity Tariff entity to be deleted
     */
    @Override
    @Transactional
    public void deleteEntity(final Tariff entity) {
        tariffSqlRepository.remove(entity);
    }

    /**
     * Gets Tariff entity by its id from database.
     * @param id id of Tariff entity to be found
     * @return founded Tariff entity
     */
    @Override
    @Transactional
    public Tariff getEntityById(final int id) {
        return (Tariff) tariffSqlRepository.getById(id);
    }

    /**
     * Gets all Tariff entities from database.
     * @return List of Tariff entities from database
     */
    @Override
    @Transactional
    public List<Tariff> getAllEntities() {
        return tariffSqlRepository.getAll();
    }
}
