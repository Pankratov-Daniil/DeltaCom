package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.Option;
import com.deltacom.app.repository.implementation.OptionSqlRepository;
import com.deltacom.app.services.api.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Operations with repository for Option entities.
 */
@Service("OptionService")
public class OptionServiceImpl implements OptionService {
    @Autowired
    private OptionSqlRepository optionSqlRepository;

    /**
     * Creates new Option entity in database.
     * @param entity Option entity to be created
     */
    @Override
    @Transactional
    public void createEntity(final Option entity) {
        optionSqlRepository.add(entity);
    }

    /**
     * Updates Option entity in database.
     * @param entity Option entity to be updated
     */
    @Override
    @Transactional
    public void updateEntity(final Option entity) {
        optionSqlRepository.update(entity);
    }

    /**
     * Deletes Option entity in database.
     * @param entity Option entity to be deleted
     */
    @Override
    @Transactional
    public void deleteEntity(final Option entity) {
        optionSqlRepository.remove(entity);
    }

    /**
     * Gets Option entity by its id from database.
     * @param id id of Option entity to be found
     * @return founded Option entity
     */
    @Override
    @Transactional
    public Option getEntityById(final int id) {
        return (Option) optionSqlRepository.getById(id);
    }

    /**
     * Gets all Option entities from database.
     * @return List of Option entities from database
     */
    @Override
    @Transactional
    public List<Option> getAllEntities() {
        return optionSqlRepository.getAll();
    }
}
