package com.deltacom.app.services.api;

import com.deltacom.app.entities.Contract;

import java.util.List;

/**
 * Interface for Contract service.
 */
public interface ContractService extends RepositoryService<Contract> {
    public List<Contract> getAllClientContractsById(int clientId);
}
