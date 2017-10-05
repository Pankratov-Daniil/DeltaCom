package com.deltacom.app.repository.api;

import com.deltacom.app.entities.Contract;

import java.util.List;

/**
 * Repository for Contract
 */
public interface ContractRepository {
    public List<Contract> getAllClientContractsById(int clientId);
}
