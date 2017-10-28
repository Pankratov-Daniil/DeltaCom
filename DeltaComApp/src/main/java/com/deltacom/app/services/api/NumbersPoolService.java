package com.deltacom.app.services.api;

import com.deltacom.app.entities.NumbersPool;

import java.util.List;

/**
 * Interface for Numbers Pool service.
 */
public interface NumbersPoolService {
    public void updateNumbersPool(NumbersPool entity);
    public List<String> getAllUnusedNumbers();
}
