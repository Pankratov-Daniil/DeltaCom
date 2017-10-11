package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.*;
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
     * Gets contract by number
     * @param number number of contract
     * @return found contract
     */
    @Override
    @Transactional
    public Contract getContractByNumber(String number) {
        return contractRepository.getContractByNumber(number);
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

        NumbersPool numbersPool = createNewNumberPool(number);
        contractRepository.add(createNewContract(clientService.getClientById(clientId),
                numbersPool,
                tariffService.getTariffById(tariffId),
                options));
        numbersPoolService.updateNumbersPool(numbersPool);
        return true;
    }

    private NumbersPool createNewNumberPool(String number) {
        NumbersPool numbersPool = new NumbersPool();
        numbersPool.setNumber(number);
        numbersPool.setUsed(true);

        return numbersPool;
    }

    private Contract createNewContract(Client client, NumbersPool numbersPool, Tariff tariff, List<Option> options) {
        Contract contract = new Contract();
        contract.setBalance(0);
        contract.setClient(client);
        contract.setNumbersPool(numbersPool);
        contract.setTariff(tariff);
        contract.setOptions(options);

        return contract;
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

            for(Option anotherOtion : options) {
                if((anotherOtion.getId() != option.getId())&&
                        (anotherOtion.getIncompatibleOptions().contains(option) ||
                        option.getIncompatibleOptions().contains(anotherOtion)))
                    return false;
            }
        }
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

        contractRepository.update(contract);
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
