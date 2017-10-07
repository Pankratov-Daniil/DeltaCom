package com.deltacom.app.services.api;

import com.deltacom.app.entities.Option;

import java.util.List;

/**
 * Interface for Option service.
 */
public interface OptionService extends RepositoryService<Option, Integer> {
    public List<Option> getAllOptionsForTariff(int id);
}
