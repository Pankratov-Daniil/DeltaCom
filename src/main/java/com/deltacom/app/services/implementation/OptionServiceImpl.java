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
     * Creates new Option entity in database.
     * @param entity Option entity to be created
     */
    @Override
    @Transactional
    public void create(Option entity) {
        optionRepository.add(entity);
    }

    /**
     * Updates Option entity in database.
     * @param entity Option entity to be updated
     */
    @Override
    @Transactional
    public void update(Option entity) {
        optionRepository.update(entity);
    }

    /**
     * Deletes Option entity in database.
     * @param entity Option entity to be deleted
     */
    @Override
    @Transactional
    public void delete(Option entity) {
        optionRepository.remove(entity);
    }

    /**
     * Gets Option entity by its id from database.
     * @param id id of Option entity to be found
     * @return founded Option entity
     */
    @Override
    @Transactional
    public Option getById(Integer id) {
        return (Option) optionRepository.getById(id);
    }

    /**
     * Gets all Option entities from database.
     * @return List of Option entities from database
     */
    @Override
    @Transactional
    public List<Option> getAll() {
        return optionRepository.getAll();
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
