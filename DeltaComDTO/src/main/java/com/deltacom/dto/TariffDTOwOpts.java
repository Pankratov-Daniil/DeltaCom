package com.deltacom.dto;

import java.util.Arrays;
import java.util.List;

/**
 * Class for Tariff Data-Transfer Object with list of options
 */
public class TariffDTOwOpts {
    private int id;
    private String name;
    private float price;
    private List<OptionDTO> options;

    public TariffDTOwOpts() {

    }

    public TariffDTOwOpts(int id, String name, float price, List<OptionDTO> options) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.options = options;
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

    public List<OptionDTO> getOptions() {
        return options;
    }

    public void setOptions(List<OptionDTO> options) {
        this.options = options;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TariffDTOwOpts TariffDTOwOpts = (TariffDTOwOpts) o;

        if (id != TariffDTOwOpts.id) return false;
        if (Float.compare(TariffDTOwOpts.price, price) != 0) return false;
        if (name != null ? !name.equals(TariffDTOwOpts.name) : TariffDTOwOpts.name != null) return false;
        if (options != null ? !options.equals(TariffDTOwOpts.options) : TariffDTOwOpts.options != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        result = 31 * result + (options != null ? options.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TariffDTOwOpts{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", options=" + options +
                '}';
    }
}
