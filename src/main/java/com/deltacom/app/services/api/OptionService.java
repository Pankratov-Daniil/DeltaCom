package com.deltacom.app.services.api;

import com.deltacom.app.entities.Option;

import java.util.List;

/**
 * Interface for Option service.
 */
public interface OptionService {
    public Option getOptionById(Integer id);
    public List<Option> getAllOptionsForTariff(int id);
}
