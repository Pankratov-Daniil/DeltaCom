package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.Option;
import com.deltacom.app.repository.implementation.OptionRepositoryImpl;
import com.deltacom.app.services.api.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * Gets all options
     * @return list of all options
     */
    @Override
    @Transactional
    public List<Option> getAllOptions() {
        return optionRepository.getAll();
    }

    /**
     * Updates option
     * @param option otion without comp. and incomp. options
     * @param incompatibleOptionsIds list of incompatible options ids
     * @param compatibleOptionsIds list of compatible options ids
     */
    @Override
    @Transactional
    public void updateOption(Option option, String[] incompatibleOptionsIds, String[] compatibleOptionsIds,
                             String[] deletedCompOptions, String[] deletedIncompOptions) {
        Option parentOpt = getOptionById(option.getId());
        option.setIncompatibleOptions(createOptionListFromIds(parentOpt, incompatibleOptionsIds, false, true));
        option.setCompatibleOptions(createOptionListFromIds(parentOpt, compatibleOptionsIds, true, true));

        if(deletedCompOptions.length > 0) {
            List<Option> deletedOptions = createOptionListFromIds(parentOpt, deletedCompOptions, true, false);
            for(Option deletedOption : deletedOptions) {
                deletedOption.getCompatibleOptions().remove(parentOpt);
                optionRepository.update(deletedOption);
            }
        }

        if(deletedIncompOptions.length > 0) {
            List<Option> deletedOptions = createOptionListFromIds(parentOpt, deletedIncompOptions, true, false);
            for(Option deletedOption : deletedOptions) {
                deletedOption.getIncompatibleOptions().remove(parentOpt);
                optionRepository.update(deletedOption);
            }
        }

        optionRepository.update(option);
    }

    /**
     * Creates new option
     * @param option new option without comp. and incomp. options
     * @param incompatibleOptionsIds list of incompatible options ids
     * @param compatibleOptionsIds list of compatible options ids
     */
    @Override
    @Transactional
    public void createOption(Option option, String[] incompatibleOptionsIds, String[] compatibleOptionsIds) {
        Option parentOpt = getOptionById(option.getId());
        option.setIncompatibleOptions(createOptionListFromIds(parentOpt, incompatibleOptionsIds, false, true));
        option.setCompatibleOptions(createOptionListFromIds(parentOpt, compatibleOptionsIds, true, true));
        optionRepository.add(option);
    }

    /**
     * Deletes option from database
     * @param id id of option to delete
     */
    @Override
    @Transactional
    public void deleteOption(int id) {
        optionRepository.remove(getOptionById(id));
    }

    /**
     * Creates list of options from their ids
     * @param parentOpt parent option
     * @param ids list of options ids
     * @return list of options
     */
    private List<Option> createOptionListFromIds(Option parentOpt, String[] ids, boolean compatible, boolean needCheck) {
        List<Option> options = new ArrayList<>();
        if(ids != null) {
            for (String optionId : ids) {
                int optId = Integer.parseInt(optionId);
                if (optId != parentOpt.getId()) {
                    Option option = getOptionById(optId);
                    if(!needCheck) {
                        options.add(option);
                    } else {
                        // checks if other option don't have link to this option
                        if ((compatible && !option.getCompatibleOptions().contains(parentOpt)) ||
                                (!compatible && !option.getIncompatibleOptions().contains(parentOpt))) {
                            options.add(option);
                        }
                    }
                }
            }
        }
        return options;
    }
}
