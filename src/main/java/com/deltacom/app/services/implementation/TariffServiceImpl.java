package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.Tariff;
import com.deltacom.app.repository.implementation.TariffSqlRepository;
import com.deltacom.app.services.api.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("TariffService")
public class TariffServiceImpl implements TariffService {
    @Autowired
    private TariffSqlRepository tariffSqlRepository;

    @Override
    @Transactional
    public void createEntity(Tariff entity) {
        tariffSqlRepository.add(entity);
    }

    @Override
    @Transactional
    public void updateEntity(Tariff entity) {
        tariffSqlRepository.update(entity);
    }

    @Override
    @Transactional
    public void deleteEntity(Tariff entity) {
        tariffSqlRepository.remove(entity);
    }

    @Override
    @Transactional
    public Tariff getEntityById(int id) {
        return (Tariff)tariffSqlRepository.getById(id);
    }

    @Override
    @Transactional
    public List<Tariff> getAllEntities() {
        return tariffSqlRepository.getAll();
    }
}
