package com.deltacom.dto;

import java.util.Arrays;

/**
 * Class for Option Data-Transfer Object
 */
public class OptionDTO {
    private int id;
    private String name;
    private float price;
    private float connectionCost;
    private String[] incompatibleOptions;
    private String[] compatibleOptions;

    public OptionDTO() {

    }

    public OptionDTO(int id, String name, float price, float connectionCost, String[] incompatibleOptions, String[] compatibleOptions) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.connectionCost = connectionCost;
        this.incompatibleOptions = incompatibleOptions;
        this.compatibleOptions = compatibleOptions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getConnectionCost() {
        return connectionCost;
    }

    public void setConnectionCost(float connectionCost) {
        this.connectionCost = connectionCost;
    }

    public String[] getIncompatibleOptions() {
        return incompatibleOptions;
    }

    public void setIncompatibleOptions(String[] incompatibleOptions) {
        this.incompatibleOptions = incompatibleOptions;
    }

    public String[] getCompatibleOptions() {
        return compatibleOptions;
    }

    public void setCompatibleOptions(String[] compatibleOptions) {
        this.compatibleOptions = compatibleOptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OptionDTO optionDTO = (OptionDTO) o;

        if (id != optionDTO.id) return false;
        if (Float.compare(optionDTO.price, price) != 0) return false;
        if (Float.compare(optionDTO.connectionCost, connectionCost) != 0) return false;
        if (name != null ? !name.equals(optionDTO.name) : optionDTO.name != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(incompatibleOptions, optionDTO.incompatibleOptions)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(compatibleOptions, optionDTO.compatibleOptions);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        result = 31 * result + (connectionCost != +0.0f ? Float.floatToIntBits(connectionCost) : 0);
        result = 31 * result + Arrays.hashCode(incompatibleOptions);
        result = 31 * result + Arrays.hashCode(compatibleOptions);
        return result;
    }

    @Override
    public String toString() {
        return "OptionDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", connectionCost=" + connectionCost +
                ", incompatibleOptions=" + Arrays.toString(incompatibleOptions) +
                ", compatibleOptions=" + Arrays.toString(compatibleOptions) +
                '}';
    }
}
