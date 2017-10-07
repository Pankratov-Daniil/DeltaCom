package com.deltacom.app.services.api;

import com.deltacom.app.entities.NumbersPool;

import java.util.List;

/**
 * Interface for Numbers Pool service.
 */
public interface NumbersPoolService extends RepositoryService<NumbersPool, String> {
    public List<String> getAllUnusedNumbers();
}
