package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.Tariff;
import com.deltacom.app.repository.implementation.TariffRepositoryImpl;
import com.deltacom.app.services.api.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Operations with repository for Tariff entities.
 */
@Service("TariffService")
public class TariffServiceImpl implements TariffService {
    @Autowired
    private TariffRepositoryImpl tariffRepository;

    /**
     * Gets Tariff entity by its id from database.
     * @param id id of Tariff entity to be found
     * @return founded Tariff entity
     */
    @Transactional
    public Tariff getTariffById(Integer id) {
        return (Tariff) tariffRepository.getById(id);
    }

    /**
     * Gets all Tariff entities from database.
     * @return List of Tariff entities from database
     */
    @Transactional
    public List<Tariff> getAllTariffs() {
        return tariffRepository.getAll();
    }
}
