package com.deltacom.app.services.api;

import java.util.List;

/**
 * Interface for repository service.
 */
public interface RepositoryService<T> {
    public void createEntity(T entity);
    public void updateEntity(T entity);
    public void deleteEntity(T entity);

    public T getEntityById(int id);
    public List<T> getAllEntities();
}
