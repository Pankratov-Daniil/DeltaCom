package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.*;
import com.deltacom.app.exceptions.ContractException;
import com.deltacom.app.repository.implementation.ContractRepositoryImpl;
import com.deltacom.app.services.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
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
     * Gets all Contracts for client by his email
     * @param email client email
     * @return list of contracts for client
     */
    @Override
    @Transactional
    public List<Contract> getAllClientContractsByEmail(String email) {
        int id = clientService.getClientByEmail(email).getId();
        try {
            return contractRepository.getAllClientContractsById(id);
        } catch (PersistenceException ex) {
            throw new ContractException("Contracts wasn't gotten by email: ", ex);
        }
    }

    /**
     * Gets all Contracts by tariff
     * @param tariff tariff of contract
     * @return list of contracts
     */
    @Override
    @Transactional
    public List<Contract> getAllContractsByTariff(Tariff tariff) {
        try {
            return contractRepository.getAllContractsByTariff(tariff);
        } catch (PersistenceException ex) {
            throw new ContractException("Contracts wasn't gotten: ", ex);
        }
    }

    /**
     * Gets contract by number
     * @param number number of contract
     * @return found contract
     */
    @Override
    @Transactional
    public Contract getContractByNumber(String number) {
        try {
            return contractRepository.getContractByNumber(number);
        } catch (PersistenceException ex) {
            throw new ContractException("Contract wasn't gotten by number: ", ex);
        }
    }

    /**
     * Gets contract by id
     * @param id id of contract
     * @return found contract
     */
    @Override
    @Transactional
    public Contract getContractById(int id) {
        try {
            return contractRepository.getById(id);
        } catch (PersistenceException ex) {
            throw new ContractException("Contract wasn't gotten by id: ", ex);
        }
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
    public boolean addNewContract(int clientId, String number, int tariffId, String[] selectedOptions) {
        List<Option> options = getOptionsFromIds(selectedOptions);

        if(!checkOptions(options))
            return false;

        NumbersPool numbersPool = new NumbersPool(number, true);
        Contract contract = new Contract(clientService.getClientById(clientId),
                numbersPool,
                tariffService.getTariffById(tariffId),
                options);
        try {
            contractRepository.add(contract);
        } catch (PersistenceException ex) {
            throw new ContractException("Contract wasn't added: ", ex);
        }
        numbersPoolService.updateNumbersPool(numbersPool);
        return true;
    }

    /**
     * Checks if options have correct incompatible and compatible options
     * @param options options to check
     * @return true if check passed, false if not
     */
    private boolean checkOptions(List<Option> options) {
        for(Option option : options) {
            for(Option compOption : option.getCompatibleOptions()) {
                if(!options.contains(compOption))
                    return false;
            }

            for(Option anotherOption : options) {
                if((anotherOption.getId() != option.getId())&&
                        (anotherOption.getIncompatibleOptions().contains(option) ||
                        option.getIncompatibleOptions().contains(anotherOption)))
                    return false;
            }
        }
        return true;
    }

    /**
     * Block or unblock contract
     * @param contractId contract id
     * @param blockContract true if need to block, false otherwise
     * @param blockedByOperator true if blocked by operator, false otherwise
     */
    @Override
    @Transactional
    public void blockContract(int contractId, boolean blockContract, boolean blockedByOperator) {
        Contract contract = getContractById(contractId);
        contract.setBlocked(blockContract);
        if(blockedByOperator) {
            contract.setBlockedByOperator(blockContract);
        }
        try {
            contractRepository.update(contract);
        } catch (PersistenceException ex) {
            throw new ContractException("Contract wasn't blocked: ", ex);
        }
    }

    /**
     * Updates contract
     * @param contractNumber number of contract
     * @param newTariffId new tariff id
     * @param newOptionsId new options id
     */
    @Override
    @Transactional
    public void updateContract(String contractNumber, String newTariffId, String[] newOptionsId) {
        Contract contract = getContractByNumber(contractNumber);
        contract.setTariff(tariffService.getTariffById(Integer.parseInt(newTariffId)));
        contract.setOptions(getOptionsFromIds(newOptionsId));

        try {
            contractRepository.update(contract);
        } catch (PersistenceException ex) {
            throw new ContractException("Contract wasn't updated: ", ex);
        }
    }

    /**
     * Removes contract
     * @param id id of contract to be removed
     */
    @Override
    @Transactional
    public void deleteContract(int id) {
        try {
            Contract contract = getContractById(id);
            NumbersPool numbersPool = contract.getNumbersPool();
            contractRepository.remove(contract);
            numbersPool.setUsed(false);
            numbersPoolService.updateNumbersPool(numbersPool);
        } catch (PersistenceException ex) {
            throw new ContractException("Contract wasn't deleted: ", ex);
        }
    }

    /**
     * Creates new options list
     * @param optionsIds ids of options
     * @return list of options
     */
    private List<Option> getOptionsFromIds(String[] optionsIds) {
        ArrayList<Option> options = new ArrayList<>();
        for(String optionId : optionsIds)
            options.add(optionService.getOptionById(Integer.parseInt(optionId)));
        return options;
    }
}
