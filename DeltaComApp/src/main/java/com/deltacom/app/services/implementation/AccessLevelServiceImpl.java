package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.AccessLevel;
import com.deltacom.app.exceptions.AccessLevelException;
import com.deltacom.app.repository.api.AccessLevelRepository;
import com.deltacom.app.services.api.AccessLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Operations with repository for AccessLevel entities.
 */
@Service("AccessLevelService")
public class AccessLevelServiceImpl implements AccessLevelService {
    @Autowired
    private AccessLevelRepository accessLevelRepository;

    /**
     * Gets all AccessLevel entities from database.
     * @return List of AccessLevel entities from database
     */
    @Override
    @Transactional
    public List<AccessLevel> getAllAccessLevels() {
        try {
            return accessLevelRepository.getAll();
        } catch (PersistenceException ex) {
            throw new AccessLevelException("Access levels wasn't gotten: ", ex);
        }
    }
}
