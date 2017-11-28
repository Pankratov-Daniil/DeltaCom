package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.*;
import com.deltacom.app.exceptions.ContractException;
import com.deltacom.app.repository.api.ContractRepository;
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
    private ContractRepository contractRepository;
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
        try {
            if(email == null) {
                throw new PersistenceException("Email cannot be null.");
            }
            Client client = clientService.getClientByEmail(email);
            if(client == null) {
                throw new PersistenceException("Client wasn't found");
            }
            int id = client.getId();
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
            if(tariff == null) {
                throw new PersistenceException("Tariff cannot be null.");
            }
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
            if(number == null) {
                throw new PersistenceException("Number cannot be null.");
            }
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
     * Top up balance by value
     * @param value value to add to balance
     */
    @Override
    @Transactional
    public void addBalance(int contractId, int value) {
        try {
            Contract contract = getContractById(contractId);
            contract.setBalance(contract.getBalance() + value);
            contractRepository.update(contract);
        } catch (PersistenceException ex) {
            throw new ContractException("Can't top up balance: ", ex);
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
    public void addNewContract(int clientId, String number, int tariffId, int[] selectedOptions) {
        List<Option> options = getOptionsFromIds(selectedOptions);
        try {
            if(number == null) {
                throw new PersistenceException("Number cannot be null.");
            }
            NumbersPool numbersPool = new NumbersPool(number, true);

            if(!checkOptions(options)) {
                throw new PersistenceException("Check option failed");
            }

            Client client = clientService.getClientById(clientId);
            if(client == null) {
                throw new PersistenceException("Client wasn't found");
            }
            Tariff tariff = tariffService.getTariffById(tariffId);
            if(tariff == null) {
                throw new PersistenceException("Tariff wasn't found");
            }
            if(selectedOptions == null || selectedOptions.length == 0) {
                throw new PersistenceException("Options cannot be empty");
            }
            Contract contract = new Contract(client, numbersPool, tariff, options);
            contractRepository.add(contract);
            numbersPoolService.updateNumbersPool(numbersPool);
        } catch (PersistenceException ex) {
            throw new ContractException("Contract wasn't added: ", ex);
        }
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
        try {
            Contract contract = getContractById(contractId);
            if(contract == null) {
                throw new PersistenceException("Contract doesn't exists");
            }
            contract.setBlocked(blockContract);
            if(blockedByOperator) {
                contract.setBlockedByOperator(blockContract);
            }
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
    public void updateContract(String contractNumber, int newTariffId, int[] newOptionsId) {
        try {
            Contract contract = getContractByNumber(contractNumber);
            if(contract == null) {
                throw new PersistenceException("Contract wasn't found");
            }
            Tariff tariff = tariffService.getTariffById(newTariffId);
            if(tariff == null) {
                throw new PersistenceException("Tariff wasn't found");
            }
            if(newOptionsId == null || newOptionsId.length == 0) {
                throw new PersistenceException("Options cannot be empty.");
            }

            Tariff oldTariff = contract.getTariff();
            List<Option> oldOptions = contract.getOptions();
            List<Option> newOptions = getOptionsFromIds(newOptionsId);
            float balance = contract.getBalance();

            if(oldTariff.getId() == newTariffId) {
                for(Option newOption : newOptions) {
                    if(!oldOptions.contains(newOption)) {
                        balance -= newOption.getConnectionCost();
                    }
                }
            } else {
                balance -= tariff.getPrice();
                for(Option option : newOptions) {
                    balance -= option.getConnectionCost();
                }
            }
            if(balance < 0) {
                throw new PersistenceException("Balance cannot be less than 0.");
            }

            contract.setTariff(tariff);
            contract.setOptions(newOptions);
            contract.setBalance(balance);

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
            if(contract == null) {
                throw new PersistenceException("Contract wasn't found");
            }
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
    private List<Option> getOptionsFromIds(int[] optionsIds) {
        ArrayList<Option> options = new ArrayList<>();
        for(int optionId : optionsIds)
            options.add(optionService.getOptionById(optionId));
        return options;
    }
}
