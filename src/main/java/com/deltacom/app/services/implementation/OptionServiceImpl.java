package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.Option;
import com.deltacom.app.repository.implementation.OptionRepositoryImpl;
import com.deltacom.app.services.api.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Operations with repository for Option entities.
 */
@Service("OptionService")
public class OptionServiceImpl implements OptionService {
    @Autowired
    private OptionRepositoryImpl optionRepository;

    /**
     * Gets Option entity by its id from database.
     * @param id id of Option entity to be found
     * @return founded Option entity
     */
    @Transactional
    public Option getOptionById(Integer id) {
        return (Option) optionRepository.getById(id);
    }

    /**
     * Gets all options available for tariff by tariff id
     * @param id tariff id
     * @return list of options available for tariff
     */
    @Override
    @Transactional
    public List<Option> getAllOptionsForTariff(int id) {
        return optionRepository.getAllOptionsForTariff(id);
    }
}
