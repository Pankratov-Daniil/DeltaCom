package com.deltacom.app.entities;

import java.util.Arrays;

/**
 * Class for contract data-transfer object
 */
public class ContractDTO {
    private int clientId;
    private String number;
    private int tariffId;
    private int[] optionsIds;

    public ContractDTO() {

    }

    public ContractDTO(int clientId, String number, int tariffId, int[] optionsIds) {
        this.clientId = clientId;
        this.number = number;
        this.tariffId = tariffId;
        this.optionsIds = optionsIds;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getTariffId() {
        return tariffId;
    }

    public void setTariffId(int tariffId) {
        this.tariffId = tariffId;
    }

    public int[] getOptionsIds() {
        return optionsIds;
    }

    public void setOptionsIds(int[] optionsIds) {
        this.optionsIds = optionsIds;
    }

    @Override
    public String toString() {
        return "ContractDTO{" +
                "clientId=" + clientId +
                ", number='" + number + '\'' +
                ", tariffId=" + tariffId +
                ", optionsIds=" + Arrays.toString(optionsIds) +
                '}';
    }
}
