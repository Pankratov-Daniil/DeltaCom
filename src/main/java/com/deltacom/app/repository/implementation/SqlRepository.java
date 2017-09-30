package com.deltacom.app.repository.implementation;

import com.deltacom.app.repository.api.Repository;
import com.deltacom.app.repository.specifications.api.Specification;
import com.deltacom.app.repository.specifications.api.SqlSpecification;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

/**
 * Provides communication with MySQL database.
 */
public class SqlRepository<T> implements Repository<T> {
    @PersistenceContext
    private EntityManager entityManager;

    private final Class<T> entityClass;

    public SqlRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Adds entity to database.
     * @param item entity to addition
     */
    @Override
    public void add(T item) {
        add(Collections.singletonList(item));
    }

    /**
     * Adds entities from list to database.
     * @param items entities for addition
     */
    @Override
    public void add(List<T> items) {
        for (T item : items) {
            entityManager.persist(item);
        }
    }

    /**
     * Removing entity from database.
     * @param item entity for deleting
     */
    @Override
    public void remove(T item) {
        entityManager.remove(entityManager.merge(item));
    }

    /**
     * Removes entity from database.
     * @param specification describes how entity for deletion will be find
     */
    @Override
    public void remove(final Specification specification) {
        for (T entity : query(specification)) {
            remove(entity);
        }
    }

    /**
     * Updates entity in database.
     * @param item entity for upgrade
     */
    @Override
    public void update(T item) {
        entityManager.merge(item);
    }

    /**
     * Gets entity from database by its id.
     * @param id  id of entity to be found
     * @return found entity
     */
    @Override
    public T getById(int id) {
        return (T) entityManager.find(entityClass, id);
    }

    /**
     * Get all entities with one type from database.
     * @return List of entities
     */
    @Override
    public List<T> getAll() {
        return (List<T>) entityManager.createQuery("from " + entityClass.getSimpleName()).getResultList();
    }

    /**
     * Query to database for retrieving entities.
     * @param specification how query will be built
     * @return List of found entities
     */
    @Override
    public List<T> query(Specification specification) {
        SqlSpecification sqlSpecification = (SqlSpecification) specification;

        return (List<T>) entityManager.createQuery(sqlSpecification.toSqlQuery()).getResultList();
    }
}
