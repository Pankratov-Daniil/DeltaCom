package com.deltacom.app.repository.api;

import java.util.List;

/**
 * Generic repository interface
 */
public interface GenericRepository<T> {
    public void add(T item);
    public void update(T item);
    public void remove(T item);
    public T getById(int id);
    public List<T> getAll();
}
