package com.deltacom.app.repository.implementation;

import com.deltacom.app.repository.api.Repository;
import com.deltacom.app.repository.specifications.api.Specification;
import com.deltacom.app.repository.specifications.api.SqlSpecification;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

/**
 * Provides communication with MySQL database
 */
public class SqlRepository<T> implements Repository<T> {
    @PersistenceContext
    private EntityManager entityManager;

    private Class<T> entityClass;

    public SqlRepository(Class<T> entityClass){
        this.entityClass = entityClass;
    }

    /**
     * Adds entity to database
     * @param item entity to addition
     */
    @Override
    public void add(T item) {
        add(Collections.singletonList(item));
    }

    /**
     * Adds entities from list to database
     * @param items entities for addition
     */
    @Override
    public void add(List<T> items) {
        entityManager.getTransaction().begin();
        for(T item : items){
            entityManager.persist(item);
        }
        entityManager.getTransaction().commit();
    }

    /**
     * Removing entity from database
     * @param item entity for deleting
     */
    @Override
    public void remove(T item) {
        entityManager.getTransaction().begin();
        entityManager.remove(item);
        entityManager.getTransaction().commit();
    }

    /**
     * Removes entity from database
     * @param specification describes how entity for deletion will be find
     */
    @Override
    public void remove(Specification specification) {
        for(T entity : query(specification)) {
            remove(entity);
        }
    }

    /**
     * Updates entity in database
     * @param item entity for upgrade
     */
    @Override
    public void update(T item) {
        entityManager.getTransaction().begin();
        entityManager.merge(item);
        entityManager.getTransaction().commit();
    }

    /**
     * Get all entities with one type from database
     * @return List of entities
     */
    @Override
    public List<T> getAll() {
        return (List<T>)entityManager.createQuery("from " + entityClass.getSimpleName()).getResultList();
    }

    @Override
    public List<T> query(Specification specification) {
        final SqlSpecification sqlSpecification = (SqlSpecification) specification;

        return (List<T>)entityManager.createQuery(sqlSpecification.toSqlQuery()).getResultList();
    }
}
