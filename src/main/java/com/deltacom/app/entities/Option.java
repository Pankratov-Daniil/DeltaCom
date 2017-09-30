package com.deltacom.app.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Class for options that can be in tariff.
 */
@Entity
@Table(name = "option")
public class Option {
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
    @Basic
    @Column(name = "connectionCost")
    private float connectionCost;
    @ManyToMany(mappedBy = "options")
    private List<Contract> contracts;
    @ManyToMany
    @JoinTable(name = "incompatible_options",
            joinColumns = @JoinColumn(name = "idOption1"),
            inverseJoinColumns = @JoinColumn(name = "idOption2"))
    private List<Option> incompatibleOptions;
    @ManyToMany
    @JoinTable(name = "compatible_options",
            joinColumns = @JoinColumn(name = "idOption1"),
            inverseJoinColumns = @JoinColumn(name = "idOption2"))
    private List<Option> compatibleOptions;

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

    @Override
    public String toString() {
        return "Option{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", connectionCost=" + connectionCost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Option option = (Option) o;

        if (id != option.id) return false;
        if (Float.compare(option.price, price) != 0) return false;
        if (Float.compare(option.connectionCost, connectionCost) != 0) return false;
        if (name != null ? !name.equals(option.name) : option.name != null) return false;
        return contracts != null ? contracts.equals(option.contracts) : option.contracts == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        result = 31 * result + (connectionCost != +0.0f ? Float.floatToIntBits(connectionCost) : 0);
        result = 31 * result + (contracts != null ? contracts.hashCode() : 0);
        return result;
    }
}