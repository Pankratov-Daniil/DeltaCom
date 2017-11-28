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
    public Contract getContractById(int id);
    public void addBalance(int contractId, int value);
    public void addNewContract(int clientId, String number, int tariffId, int[] selectedOptions);
    public void blockContract(int contractId, boolean blockContract, boolean blockedByAdmin);
    public void updateContract(String contractNumber, int newTariffId, int[] newOptionsId);
    public void deleteContract(int contractId);
}
