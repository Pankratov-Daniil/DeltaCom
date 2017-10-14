package com.deltacom.app.repository.api;

import com.deltacom.app.entities.Contract;
import com.deltacom.app.entities.Tariff;

import java.util.List;

/**
 * Repository for Contract
 */
public interface ContractRepository {
    public List<Contract> getAllClientContractsById(int clientId);
    public Contract getContractByNumber(String number);
    public List<Contract> getAllContractsByTariff(Tariff tariff);
}
