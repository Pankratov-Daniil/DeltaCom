package com.deltacom.app.services.api;

import com.deltacom.app.entities.Contract;

import java.util.List;

/**
 * Interface for Contract service.
 */
public interface ContractService extends RepositoryService<Contract, Integer> {
    public List<Contract> getAllClientContractsByEmail(String email);
    public boolean createNewContract(int clientId, String number, int tariffId, String[] selectedOptions);
    public void blockContract(int contractId, boolean blockContract);
}
