package com.deltacom.app.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.List;

/**
 * Class for options that can be in tariff.
 */
@Entity
@Table(name = "`option`")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;
    @Basic
    @Column(name = "price", nullable = false)
    private float price;
    @Basic
    @Column(name = "connectionCost", nullable = false)
    private float connectionCost;
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @JoinTable(name = "incompatible_options",
            joinColumns = @JoinColumn(name = "idOption1"),
            inverseJoinColumns = @JoinColumn(name = "idOption2"))
    private List<Option> incompatibleOptions;
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @JoinTable(name = "compatible_options",
            joinColumns = @JoinColumn(name = "idOption1"),
            inverseJoinColumns = @JoinColumn(name = "idOption2"))
    private List<Option> compatibleOptions;

    public Option() {

    }

    public Option(int id, String name, float price, float connectionCost, List<Option> compatibleOptions, List<Option> incompatibleOptions) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.connectionCost = connectionCost;
        this.compatibleOptions = compatibleOptions;
        this.incompatibleOptions = incompatibleOptions;
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

    public List<Option> getIncompatibleOptions() {
        return incompatibleOptions;
    }

    public void setIncompatibleOptions(List<Option> incompatibleOptions) {
        this.incompatibleOptions = incompatibleOptions;
    }

    public List<Option> getCompatibleOptions() {
        return compatibleOptions;
    }

    public void setCompatibleOptions(List<Option> compatibleOptions) {
        this.compatibleOptions = compatibleOptions;
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
        if (incompatibleOptions != null ? !incompatibleOptions.equals(option.incompatibleOptions) : option.incompatibleOptions != null)
            return false;
        return compatibleOptions != null ? compatibleOptions.equals(option.compatibleOptions) : option.compatibleOptions == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (Float.compare(price, 0.0f) != 0 ? Float.floatToIntBits(price) : 0);
        result = 31 * result + (Float.compare(connectionCost, 0.0f) != 0 ? Float.floatToIntBits(connectionCost) : 0);
        result = 31 * result + (incompatibleOptions != null ? incompatibleOptions.hashCode() : 0);
        result = 31 * result + (compatibleOptions != null ? compatibleOptions.hashCode() : 0);
        return result;
    }
}