package com.deltacom.app.repository.implementation;

import com.deltacom.app.repository.api.GenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class HibernateRepository<T> implements GenericRepository<T> {
    @PersistenceContext
    private EntityManager entityManager;

    private Class<T> entityClass;

    public HibernateRepository() {
        this.entityClass = (Class<T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Adds entity to database.
     * @param item entity to addition
     */
    @Override
    public void add(T item) {
        entityManager.persist(item);
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
     * Removing entity from database.
     * @param item entity for deleting
     */
    @Override
    public void remove(T item) {
        entityManager.remove(entityManager.merge(item));
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
}
