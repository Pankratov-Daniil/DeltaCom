package com.deltacom.app.entities;

import java.util.Arrays;

/**
 * Class for Tariff Data-Transfer Object
 */
public class TariffDTO {
    private int id;
    private String name;
    private float price;
    private String[] optionsIds;

    public TariffDTO() {

    }

    public TariffDTO(int id, String name, float price, String[] optionsIds) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.optionsIds = optionsIds;
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

    public String[] getOptionsIds() {
        return optionsIds;
    }

    public void setOptionsIds(String[] optionsIds) {
        this.optionsIds = optionsIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TariffDTO tariffDTO = (TariffDTO) o;

        if (id != tariffDTO.id) return false;
        if (Float.compare(tariffDTO.price, price) != 0) return false;
        if (name != null ? !name.equals(tariffDTO.name) : tariffDTO.name != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(optionsIds, tariffDTO.optionsIds);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        result = 31 * result + Arrays.hashCode(optionsIds);
        return result;
    }

    @Override
    public String toString() {
        return "TariffDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", optionsIds=" + Arrays.toString(optionsIds) +
                '}';
    }
}
