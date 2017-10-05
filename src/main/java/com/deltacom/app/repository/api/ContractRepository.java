package com.deltacom.app.repository.api;

import com.deltacom.app.entities.Contract;

import java.util.List;

public interface ContractRepository {
    public List<Contract> getAllClientContractsById(int clientId);
}
