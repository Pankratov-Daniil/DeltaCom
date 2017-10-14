package com.deltacom.app.services.api;

import com.deltacom.app.entities.Contract;
import com.deltacom.app.entities.Tariff;

import java.util.List;

/**
 * Interface for Contract service.
 */
public interface ContractService {
    public List<Contract> getAllClientContractsByEmail(String email);
    public List<Contract> getAllContractsByTariff(Tariff tariff);
    public Contract getContractByNumber(String number);
    public boolean addNewContract(int clientId, String number, int tariffId, String[] selectedOptions);
    public void blockContract(int contractId, boolean blockContract, boolean blockedByAdmin);
    public void updateContract(String contractNumber, String newTariffId, String[] newOptionsId);
}
