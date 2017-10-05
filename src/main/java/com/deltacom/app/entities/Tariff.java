package com.deltacom.app.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Class for tariff.
 */
@Entity
@Table(name = "tariff")
public class Tariff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "price")
    private float price;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "`tariff_option`",
            joinColumns = @JoinColumn(name = "idTariff"),
            inverseJoinColumns = @JoinColumn(name = "idOption"))
    private List<Option> options;


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrice() { return price; }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public List<Option> getOptions() {
        return options;
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", options=" + options +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tariff tariff = (Tariff) o;

        if (id != tariff.id) return false;
        if (Float.compare(tariff.price, price) != 0) return false;
        if (name != null ? !name.equals(tariff.name) : tariff.name != null) return false;
        return options != null ? options.equals(tariff.options) : tariff.options == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        result = 31 * result + (options != null ? options.hashCode() : 0);
        return result;
    }
}
