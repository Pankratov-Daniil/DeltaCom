package com.deltacom.app.repository.api;

import java.util.List;

/**
 * Repository for numbers pool
 */
public interface NumbersPoolRepository {
    public List<String> getAllUnusedNumbers();
}
