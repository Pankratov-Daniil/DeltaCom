package com.deltacom.app.entity;

import java.util.HashSet;
import java.util.Set;

public class Contract {
    private Long number;
    private Integer idTariff;
    private Set<Option> options;

    public void setNumber (Long number) {
        this.number = number;
    }

    public Long getNumber () {
        return number;
    }

    public void setIdTariff (Integer tariff) {
        this.idTariff = tariff;
    }

    public Integer getIdTariff() {
        return idTariff;
    }

    public void setOptions (Set<Option> options) {
        this.options = (options == null) ? new HashSet<Option>() : new HashSet<Option>(options);
    }

    public Set<Option> getOptions () {
        return new HashSet<Option>(options);
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;

        result = prime * result + number.hashCode();
        result = prime * result + idTariff.hashCode();
        result = prime * result + options.size();

        return result;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj)
            return true;
        if(!(obj instanceof Contract))
            return false;
        Contract anotherContract = (Contract)obj;

        if((this.options != null && anotherContract.options == null) ||
                (this.options == null && anotherContract.options != null) ||
                this.options.size() != anotherContract.options.size())
            return false;

        return anotherContract.number.compareTo(this.number) == 0 &&
                anotherContract.idTariff.compareTo(this.idTariff) == 0 &&
                this.options.containsAll(anotherContract.options) &&
                anotherContract.options.containsAll(this.options);
    }

    @Override
    public String toString() {
        return "Contract: \n\tnumber: " + number + "\n\tidTariff: " + idTariff;
    }
}