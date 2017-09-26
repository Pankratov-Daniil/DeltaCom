package com.deltacom.app.repository;

import java.util.List;

public interface Repository <T> {
    public void add(final T item);
    public void add(final List<T> items);
    public void remove(final T item);
    public void remove(Specification specification);
    public void update(final T item);

    public List<T> getAll();

}
