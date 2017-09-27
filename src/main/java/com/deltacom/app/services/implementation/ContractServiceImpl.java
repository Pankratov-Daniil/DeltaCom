package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.Contract;
import com.deltacom.app.repository.implementation.ContractSqlRepository;
import com.deltacom.app.services.api.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("ContractService")
public class ContractServiceImpl implements ContractService {
    @Autowired
    private ContractSqlRepository contractSqlRepository;

    @Override
    @Transactional
    public void createEntity(Contract entity) {
        contractSqlRepository.add(entity);
    }

    @Override
    @Transactional
    public void updateEntity(Contract entity) {
        contractSqlRepository.update(entity);
    }

    @Override
    @Transactional
    public void deleteEntity(Contract entity) {
        contractSqlRepository.remove(entity);
    }

    @Override
    @Transactional
    public Contract getEntityById(int id) {
        return (Contract)contractSqlRepository.getById(id);
    }

    @Override
    @Transactional
    public List<Contract> getAllEntities() {
        return contractSqlRepository.getAll();
    }
}
