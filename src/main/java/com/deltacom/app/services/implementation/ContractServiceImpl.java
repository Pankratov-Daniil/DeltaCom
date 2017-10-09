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
        NumbersPool numbersPool = new NumbersPool();
        numbersPool.setNumber(number);
        numbersPool.setUsed(true);

        ArrayList<Option> options = new ArrayList<>();
        for(String optionId : selectedOptions)
            options.add(optionService.getOptionById(Integer.parseInt(optionId)));

        Contract contract = new Contract();
        contract.setBalance(0);
        contract.setClient(clientService.getClientById(clientId));
        contract.setNumbersPool(numbersPool);
        contract.setTariff(tariffService.getTariffById(tariffId));
        contract.setOptions(options);

        contractRepository.add(contract);
        numbersPoolService.updateNumbersPool(numbersPool);
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
