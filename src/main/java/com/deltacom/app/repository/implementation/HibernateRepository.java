package com.deltacom.app.repository.implementation;

import com.deltacom.app.exceptions.RepositoryException;
import com.deltacom.app.repository.api.GenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class HibernateRepository<T, K> implements GenericRepository<T, K> {
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
    public void add(T item) throws RepositoryException {
        try {
            entityManager.persist(item);
        } catch(PersistenceException ex) {
            throw new RepositoryException("Entity didn't added: " + item, ex);
        }
    }

    /**
     * Updates entity in database.
     * @param item entity for upgrade
     */
    @Override
    public void update(T item) throws RepositoryException {
        try {
            entityManager.merge(item);
        } catch(PersistenceException ex) {
            throw new RepositoryException("Entity didn't updated: " + item, ex);
        }
    }

    /**
     * Removing entity from database.
     * @param item entity for deleting
     */
    @Override
    public void remove(T item) throws RepositoryException {
        try {
            entityManager.remove(entityManager.merge(item));
        } catch(PersistenceException ex) {
            throw new RepositoryException("Entity didn't removed: " + item, ex);
        }
    }

    /**
     * Gets entity from database by its id.
     * @param id  id of entity to be found
     * @return found entity
     */
    @Override
    public T getById(K id) throws RepositoryException {
        try {
            return entityManager.find(entityClass, id);
        } catch(PersistenceException ex) {
            throw new RepositoryException("Entities wasn't gotten: ", ex);
        }
    }

    /**
     * Get all entities with one type from database.
     * @return List of entities
     */
    @Override
    public List<T> getAll() throws RepositoryException {
        try {
            return (List<T>) entityManager.createQuery("from " + entityClass.getSimpleName()).getResultList();
        } catch(PersistenceException ex) {
            throw new RepositoryException("Collection of all entities wasn't gotten: ", ex);
        }
    }
}
