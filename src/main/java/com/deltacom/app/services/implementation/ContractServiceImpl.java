package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.Contract;
import com.deltacom.app.entities.NumbersPool;
import com.deltacom.app.entities.Option;
import com.deltacom.app.repository.implementation.ContractRepositoryImpl;
import com.deltacom.app.services.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Operations with repository for Contract entities.
 */
@Service("ContractService")
public class ContractServiceImpl implements ContractService {
    @Autowired
    private ContractRepositoryImpl contractRepository;
    @Autowired
    private TariffService tariffService;
    @Autowired
    private OptionService optionService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private NumbersPoolService numbersPoolService;

    /**
     * Creates new Contract entity in database.
     * @param entity Contract entity to be created
     */
    @Override
    @Transactional
    public void create(Contract entity) {
        contractRepository.add(entity);
    }

    /**
     * Updates Contract entity in database.
     * @param entity Contract entity to be updated
     */
    @Override
    @Transactional
    public void update(Contract entity) {
        contractRepository.update(entity);
    }

    /**
     * Deletes Contract entity in database.
     * @param entity Contract entity to be deleted
     */
    @Override
    @Transactional
    public void delete(Contract entity) {
        contractRepository.remove(entity);
    }

    /**
     * Gets Contract entity by its id from database.
     * @param id id of Contract entity to be found
     * @return founded Contract entity
     */
    @Override
    @Transactional
    public Contract getById(Integer id) {
        return (Contract) contractRepository.getById(id);
    }

    /**
     * Gets all Contract entities from database.
     * @return List of Contract entities from database
     */
    @Override
    @Transactional
    public List<Contract> getAll() {
        return contractRepository.getAll();
    }

    /**
     * Gets all Contracts for client by his email
     * @param email client email
     * @return list of contracts for client
     */
    @Override
    @Transactional
    public List<Contract> getAllClientContractsByEmail(String email) {
        return contractRepository.getAllClientContractsById(
                clientService.getClientByEmail(email).getId());
    }

    /**
     * Creates new contract
     * @param clientId client id
     * @param number selected number
     * @param tariffId selected tariff id
     * @param selectedOptions selected options for tariff
     * @return true if client successfully created, false otherwise
     */
    @Override
    @Transactional
    public boolean createNewContract(int clientId, String number, int tariffId, String[] selectedOptions) {
        Contract contract = new Contract();
        contract.setBalance(0);
        contract.setClient(clientService.getById(clientId));
        NumbersPool numbersPool = new NumbersPool();
        numbersPool.setNumber(number);
        numbersPool.setUsed(true);
        contract.setNumbersPool(numbersPool);
        contract.setTariff(tariffService.getById(tariffId));
        ArrayList<Option> options = new ArrayList<>();
        for(String optionId : selectedOptions)
            options.add(optionService.getById(Integer.parseInt(optionId)));
        contract.setOptions(options);

        contractRepository.add(contract);
        numbersPoolService.update(numbersPool);
        return true;
    }

    /**
     * Block or unblock contract
     * @param contractId contract id
     * @param blockContract true if need to block, false otherwise
     * @param blockedByOperator true if blicked by operator, false otherwise
     */
    @Override
    @Transactional
    public void blockContract(int contractId, boolean blockContract, boolean blockedByOperator) {
        Contract contract = contractRepository.getById(contractId);
        contract.setBlocked(blockContract);
        if(blockedByOperator)
            contract.setBlockedByOperator(blockContract);
        contractRepository.update(contract);
    }
}
