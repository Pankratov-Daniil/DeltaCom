package com.deltacom.app.repository.api;

import com.deltacom.app.repository.specifications.api.Specification;

import java.util.List;

/**
 * Interface for Generic Repository
 */
public interface Repository <T> {
    public void add(final T item);
    public void add(final List<T> items);
    public void remove(final T item);
    public void remove(Specification specification);
    public void update(final T item);
    public T getById(int id);
    public List<T> getAll();

    public List<T> query(Specification specification);
}
