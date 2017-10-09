package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.AccessLevel;
import com.deltacom.app.repository.api.AccessLevelRepository;
import com.deltacom.app.repository.implementation.AccessLevelRepositoryImpl;
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
    private AccessLevelRepositoryImpl accessLevelRepository;

    /**
     * Gets all AccessLevel entities from database.
     * @return List of AccessLevel entities from database
     */
    @Transactional
    public List<AccessLevel> getAllAccessLevels() {
        return accessLevelRepository.getAll();
    }
}
