package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.AccessLevel;
import com.deltacom.app.repository.api.AccessLevelRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Access level repository implementation
 */
@Repository("AccessLevel")
public class AccessLevelRepositoryImpl extends HibernateRepository<AccessLevel> implements AccessLevelRepository {
    @PersistenceContext
    private EntityManager entityManager;
}
