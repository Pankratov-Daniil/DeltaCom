package com.deltacom.app.repository.api;

import com.deltacom.app.repository.specifications.api.Specification;

import java.util.List;

/**
 * Interface for Generic Repository
 */
public interface Repository <T> {
    public void add(T item);
    public void add(List<T> items);
    public void remove(T item);
    public void remove(Specification specification);
    public void update(T item);
    public T getById(int id);
    public List<T> getAll();
    public List<T> query(Specification specification);
}
