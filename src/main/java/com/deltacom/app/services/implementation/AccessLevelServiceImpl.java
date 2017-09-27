package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.AccessLevel;
import com.deltacom.app.repository.implementation.AccessLevelSqlRepository;
import com.deltacom.app.services.api.AccessLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("AccessLevelService")
public class AccessLevelServiceImpl implements AccessLevelService {
    @Autowired
    private AccessLevelSqlRepository accessLevelRepository;

    @Override
    @Transactional
    public void createEntity(AccessLevel entity) {
        accessLevelRepository.add(entity);
    }

    @Override
    @Transactional
    public void updateEntity(AccessLevel entity) {
        accessLevelRepository.update(entity);
    }

    @Override
    @Transactional
    public void deleteEntity(AccessLevel entity) {
        accessLevelRepository.remove(entity);
    }

    @Override
    @Transactional
    public AccessLevel getEntityById(int id) {
        return (AccessLevel)accessLevelRepository.getById(id);
    }

    @Override
    @Transactional
    public List<AccessLevel> getAllEntities() {
        return accessLevelRepository.getAll();
    }
}
