package com.deltacom.app.entities;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Class for clients cart
 */
public class ClientCart implements Serializable {
    private String number;
    private String tariffId;
    private String[] optionsIds;

    public ClientCart() {

    }

    public ClientCart(String number, String tariffId, String[] optionsIds) {
        this.number = number;
        this.tariffId = tariffId;
        this.optionsIds = optionsIds;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTariffId() {
        return tariffId;
    }

    public void setTariffId(String tariffId) {
        this.tariffId = tariffId;
    }

    public String[] getOptionsIds() {
        return optionsIds;
    }

    public void setOptionsIds(String[] optionsIds) {
        this.optionsIds = optionsIds;
    }

    @Override
    public String toString() {
        return "ClientCart{" +
                "number='" + number + '\'' +
                ", tariffId='" + tariffId + '\'' +
                ", optionsIds=" + Arrays.toString(optionsIds) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientCart that = (ClientCart) o;

        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (tariffId != null ? !tariffId.equals(that.tariffId) : that.tariffId != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(optionsIds, that.optionsIds);
    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + (tariffId != null ? tariffId.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(optionsIds);
        return result;
    }
}
