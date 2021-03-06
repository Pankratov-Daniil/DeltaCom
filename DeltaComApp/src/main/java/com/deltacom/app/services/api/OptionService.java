package com.deltacom.app.services.api;

import com.deltacom.app.entities.Option;

import java.util.List;

/**
 * Interface for Option service.
 */
public interface OptionService {
    public Option getOptionById(int id);
    public List<Option> getAllOptionsForTariff(int id);
    public List<Option> getAllOptions();
    public void updateOption(Option option, String[] incompatibleOptionsIds, String[] compatibleOptionsIds);
    public void addOption(Option option, String[] incompatibleOptionsIds, String[] compatibleOptionsIds);
    public void deleteOption(int id);
}
