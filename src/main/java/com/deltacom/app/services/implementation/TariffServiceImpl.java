package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.Tariff;
import com.deltacom.app.repository.implementation.TariffRepositoryImpl;
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
    private TariffRepositoryImpl tariffRepository;

    /**
     * Creates new Tariff entity in database.
     * @param entity Tariff entity to be created
     */
    @Transactional
    public void create(Tariff entity) {
        tariffRepository.add(entity);
    }

    /**
     * Updates Tariff entity in database.
     * @param entity Tariff entity to be updated
     */
    @Override
    @Transactional
    public void update(Tariff entity) {
        tariffRepository.update(entity);
    }

    /**
     * Deletes Tariff entity in database.
     * @param entity Tariff entity to be deleted
     */
    @Override
    @Transactional
    public void delete(Tariff entity) {
        tariffRepository.remove(entity);
    }

    /**
     * Gets Tariff entity by its id from database.
     * @param id id of Tariff entity to be found
     * @return founded Tariff entity
     */
    @Override
    @Transactional
    public Tariff getById(int id) {
        return (Tariff) tariffRepository.getById(id);
    }

    /**
     * Gets all Tariff entities from database.
     * @return List of Tariff entities from database
     */
    @Override
    @Transactional
    public List<Tariff> getAll() {
        return tariffRepository.getAll();
    }
}
