package com.deltacom.app.repository.api;

import com.deltacom.app.entities.Option;

import java.util.List;

/**
 * Repository for Option
 */
public interface OptionRepository extends GenericRepository<Option, Integer>{
    public List<Option> getAllOptionsForTariff(int id);
}
