package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.Option;
import com.deltacom.app.repository.implementation.OptionSqlRepository;
import com.deltacom.app.services.api.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("OptionService")
public class OptionServiceImpl implements OptionService {
    @Autowired
    private OptionSqlRepository optionSqlRepository;

    @Override
    @Transactional
    public void createEntity(Option entity) {
        optionSqlRepository.add(entity);
    }

    @Override
    @Transactional
    public void updateEntity(Option entity) {
        optionSqlRepository.update(entity);
    }

    @Override
    @Transactional
    public void deleteEntity(Option entity) {
        optionSqlRepository.remove(entity);
    }

    @Override
    @Transactional
    public Option getEntityById(int id) {
        return (Option)optionSqlRepository.getById(id);
    }

    @Override
    @Transactional
    public List<Option> getAllEntities() {
        return optionSqlRepository.getAll();
    }
}
