package com.deltacom.app.services.api;

import java.util.List;

/**
 * Interface for repository service.
 */
public interface RepositoryService<T> {
    public void create(T entity);
    public void update(T entity);
    public void delete(T entity);
    public T getById(int id);
    public List<T> getAll();
}
