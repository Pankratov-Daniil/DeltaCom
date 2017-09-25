package com.deltacom.app.repository;

import java.util.List;

public interface Repository <T> {
    public void add(final T entity);
    public List<T> getAll();
    public void remove(final T entity);
    public void update(final T entity);
}
