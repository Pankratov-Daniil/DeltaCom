package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.Option;
import com.deltacom.app.exceptions.OptionException;
import com.deltacom.app.repository.implementation.OptionRepositoryImpl;
import com.deltacom.app.services.api.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
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
    @Override
    @Transactional
    public Option getOptionById(int id) {
        try {
            return optionRepository.getById(id);
        } catch (PersistenceException ex) {
            throw new OptionException("Option wasn't gotten by id: ", ex);
        }
    }

    /**
     * Gets all options available for tariff by tariff id
     * @param id tariff id
     * @return list of options available for tariff
     */
    @Override
    @Transactional
    public List<Option> getAllOptionsForTariff(int id) {
        try {
            return optionRepository.getAllOptionsForTariff(id);
        } catch (PersistenceException ex) {
            throw new OptionException("Options wasn't gotten by tariff id: ", ex);
        }
    }

    /**
     * Gets all options
     * @return list of all options
     */
    @Override
    @Transactional
    public List<Option> getAllOptions() {
        try {
            return optionRepository.getAll();
        } catch (PersistenceException ex) {
            throw new OptionException("Options wasn't gotten: ", ex);
        }
    }

    /**
     * Updates option
     * @param option option without comp. and incomp. options
     * @param incompatibleOptionsIds list of incompatible options ids
     * @param compatibleOptionsIds list of compatible options ids
     */
    @Override
    @Transactional
    public void updateOption(Option option, String[] incompatibleOptionsIds, String[] compatibleOptionsIds) {
        try {
            if(option == null){
                throw new PersistenceException("Option cannot be null.");
            }
            if(incompatibleOptionsIds == null || incompatibleOptionsIds.length == 0) {
                throw new PersistenceException("Incompatible options list cannot be empty.");
            }
            if(compatibleOptionsIds == null || compatibleOptionsIds.length == 0) {
                throw new PersistenceException("Compatible options list cannot be empty.");
            }
            option.setIncompatibleOptions(createOptionListFromIds(incompatibleOptionsIds));
            option.setCompatibleOptions(createOptionListFromIds(compatibleOptionsIds));
            optionRepository.update(option);
        } catch (PersistenceException ex) {
            throw new OptionException("Option wasn't updated: ", ex);
        }
    }

    /**
     * Creates new option
     * @param option new option without comp. and incomp. options
     * @param incompatibleOptionsIds list of incompatible options ids
     * @param compatibleOptionsIds list of compatible options ids
     */
    @Override
    @Transactional
    public void addOption(Option option, String[] incompatibleOptionsIds, String[] compatibleOptionsIds) {
        try{
            if(option == null){
                throw new PersistenceException("Option cannot be null.");
            }
            if(incompatibleOptionsIds == null || incompatibleOptionsIds.length == 0) {
                throw new PersistenceException("Incompatible options list cannot be empty.");
            }
            if(compatibleOptionsIds == null || compatibleOptionsIds.length == 0) {
                throw new PersistenceException("Compatible options list cannot be empty.");
            }
            option.setIncompatibleOptions(createOptionListFromIds(incompatibleOptionsIds));
            option.setCompatibleOptions(createOptionListFromIds(compatibleOptionsIds));
            optionRepository.add(option);
        } catch (PersistenceException ex) {
            throw new OptionException("Option wasn't added: ", ex);
        }
    }

    /**
     * Deletes option from database
     * @param id id of option to delete
     */
    @Override
    @Transactional
    public void deleteOption(int id) {
        try {
            Option option = getOptionById(id);
            if(option == null) {
                throw new PersistenceException("Option wasn't found");
            }
            optionRepository.remove(option);
        } catch (PersistenceException ex) {
            throw new OptionException("Option wasn't deleted: ", ex);
        }
    }

    /**
     * Creates list of options from their ids
     * @param ids list of options ids
     * @return list of options
     */
    private List<Option> createOptionListFromIds(String[] ids) {
        List<Option> options = new ArrayList<>();
        if(ids != null) {
            for (String optionId : ids) {
                options.add(getOptionById(Integer.parseInt(optionId)));
            }
        }
        return options;
    }
}
