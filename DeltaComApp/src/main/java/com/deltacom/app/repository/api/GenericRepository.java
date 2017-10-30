package com.deltacom.app.repository.api;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Generic repository interface
 */
@Repository
public interface GenericRepository<T, K> {
    public void add(T item);
    public void update(T item);
    public void remove(T item);
    public T getById(K id);
    public List<T> getAll();
}
