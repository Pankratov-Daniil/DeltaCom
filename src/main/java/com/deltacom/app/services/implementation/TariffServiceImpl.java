package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.Option;
import com.deltacom.app.entities.Tariff;
import com.deltacom.app.repository.implementation.OptionRepositoryImpl;
import com.deltacom.app.repository.implementation.TariffRepositoryImpl;
import com.deltacom.app.services.api.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Operations with repository for Tariff entities.
 */
@Service("TariffService")
public class TariffServiceImpl implements TariffService {
    @Autowired
    private TariffRepositoryImpl tariffRepository;
    @Autowired
    private OptionRepositoryImpl optionRepository;

    /**
     * Gets Tariff entity by its id from database.
     * @param id id of Tariff entity to be found
     * @return founded Tariff entity
     */
    @Override
    @Transactional
    public Tariff getTariffById(Integer id) {
        return (Tariff) tariffRepository.getById(id);
    }

    /**
     * Gets all Tariff entities from database.
     * @return List of Tariff entities from database
     */
    @Override
    @Transactional
    public List<Tariff> getAllTariffs() {
        return tariffRepository.getAll();
    }

    /**
     * Creates new tariff
     * @param tariff tariff without options
     * @param tariffOptionsIds tariff options ids
     */
    @Override
    @Transactional
    public void createTariff(Tariff tariff, String[] tariffOptionsIds) {
        tariff.setOptions(createOptionsListFromIds(tariffOptionsIds));
        tariffRepository.add(tariff);
    }

    /**
     * Creates list of options from ids
     * @param ids options ids
     * @return list of options
     */
    private List<Option> createOptionsListFromIds(String[] ids) {
        List<Option> options = new ArrayList<>();
        for (String optionId : ids) {
            options.add(optionRepository.getById(Integer.parseInt(optionId)));
        }
        return options;
    }

    /**
     * Deleted tariff
     * @param id id of tariff to delete
     */
    @Override
    @Transactional
    public void deleteTariff(int id) {
        tariffRepository.remove(getTariffById(id));
    }
}
