package com.deltacom.app.entities;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

/**
 * Class for pool of numbers.
 */
@Entity
@Table(name = "number_pool")
public class NumbersPool {
    @Id
    @Column(name = "number", nullable = false)
    @NotBlank
    private String number;
    @Basic
    @Column(name = "used", nullable = false)
    private boolean used;

    public NumbersPool() {

    }

    public NumbersPool(String number, boolean used) {
        this.number = number;
        this.used = used;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NumbersPool that = (NumbersPool) o;

        if (used != that.used) return false;
        return number != null ? number.equals(that.number) : that.number == null;
    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + (used ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NumbersPool{" +
                "number='" + number + '\'' +
                ", used=" + used +
                '}';
    }
}
