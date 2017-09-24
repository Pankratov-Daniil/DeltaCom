package com.deltacom.app.entity;

import java.util.HashSet;
import java.util.Set;

public class Tariff {
    private Integer id;
    private String name;
    private Float price;
    private Set<Option> options;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getPrice() { return price; }

    public void setOptions(Set<Option> options) {
        this.options = (options == null) ? new HashSet<Option>() : new HashSet<Option>(options);
    }

    public Set<Option> getOptions() {
        return new HashSet<Option>(options);
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;

        result = prime * result + id;
        result = prime * result + Float.floatToIntBits(price);
        result = prime * result + options.size();

        return result;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj)
            return true;
        if(!(obj instanceof Tariff))
            return false;
        Tariff anotherTariff = (Tariff)obj;

        if((this.options != null && anotherTariff.options == null) ||
                (this.options == null && anotherTariff.options != null) ||
                this.options.size() != anotherTariff.options.size())
            return false;

        return anotherTariff.id.compareTo(this.id) == 0 &&
                anotherTariff.name.equals(this.name) &&
                anotherTariff.price.compareTo(this.price) == 0 &&
                this.options.containsAll(anotherTariff.options) &&
                anotherTariff.options.containsAll(this.options);
    }
}
