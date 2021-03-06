package com.deltacom.app.repository.api;

import com.deltacom.app.entities.NumbersPool;

import java.util.List;

/**
 * Repository for numbers pool
 */
public interface NumbersPoolRepository extends GenericRepository<NumbersPool, String>{
    public List<String> getAllUnusedNumbers();
}
