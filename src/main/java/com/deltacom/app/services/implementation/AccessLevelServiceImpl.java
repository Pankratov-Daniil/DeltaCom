package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.AccessLevel;
import com.deltacom.app.repository.implementation.AccessLevelSqlRepository;
import com.deltacom.app.services.api.AccessLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Operations with repository for AccessLevel entities.
 */
@Service("AccessLevelService")
public class AccessLevelServiceImpl implements AccessLevelService {
    @Autowired
    private AccessLevelSqlRepository accessLevelRepository;

    /**
     * Creates new AccessLevel entity in database.
     * @param entity AccessLevel entity to be created
     */
    @Override
    @Transactional
    public void createEntity(AccessLevel entity) {
        accessLevelRepository.add(entity);
    }

    /**
     * Updates AccessLevel entity in database.
     * @param entity AccessLevel entity to be updated
     */
    @Override
    @Transactional
    public void updateEntity(AccessLevel entity) {
        accessLevelRepository.update(entity);
    }

    /**
     * Deletes AccessLevel entity in database.
     * @param entity AccessLevel entity to be deleted
     */
    @Override
    @Transactional
    public void deleteEntity(AccessLevel entity) {
        accessLevelRepository.remove(entity);
    }

    /**
     * Gets AccessLevel entity by its id from database.
     * @param id id of AccessLevel entity to be found
     * @return founded AccessLevel entity
     */
    @Override
    @Transactional
    public AccessLevel getEntityById(int id) {
        return (AccessLevel) accessLevelRepository.getById(id);
    }

    /**
     * Gets all AccessLevel entities from database.
     * @return List of AccessLevel entities from database
     */
    @Override
    @Transactional
    public List<AccessLevel> getAllEntities() {
        return accessLevelRepository.getAll();
    }
}
