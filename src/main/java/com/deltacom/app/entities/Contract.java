package com.deltacom.app.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Class for clients contracts.
 */
@Entity
@Table(name = "contract")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idContract")
    private long id;
    @Basic
    @Column(name = "numberContract")
    private String number;
    @Basic
    @Column(name = "blocked")
    private boolean blocked;
    @Basic
    @Column(name = "blockedByOperator")
    private boolean blockedByOperator;
    @Basic
    @Column(name = "balance")
    private float balance;

    @ManyToOne
    @JoinColumn(name = "idClient")
    private Client client;

    @ManyToMany
    @JoinTable(name = "contract_option",
                joinColumns = @JoinColumn(name = "idContract"),
                inverseJoinColumns = @JoinColumn(name = "idOption"))
    private List<Option> options;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isBlockedByOperator() {
        return blockedByOperator;
    }

    public void setBlockedByOperator(boolean blockedByOperator) {
        this.blockedByOperator = blockedByOperator;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contract contract = (Contract) o;

        if (id != contract.id) return false;
        if (blocked != contract.blocked) return false;
        if (blockedByOperator != contract.blockedByOperator) return false;
        if (Float.compare(contract.balance, balance) != 0) return false;
        return number != null ? number.equals(contract.number) : contract.number == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (blocked ? 1 : 0);
        result = 31 * result + (blockedByOperator ? 1 : 0);
        result = 31 * result + (balance != +0.0f ? Float.floatToIntBits(balance) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", blocked=" + blocked +
                ", blockedByOperator=" + blockedByOperator +
                ", balance=" + balance +
                '}';
    }
}