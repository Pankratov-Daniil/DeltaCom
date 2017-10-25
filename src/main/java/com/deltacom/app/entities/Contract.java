package com.deltacom.app.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Class for clients contracts.
 */
@Entity
@Table(name = "contract")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @OneToOne
    @JoinColumn(name = "number", nullable = false)
    private NumbersPool numbersPool;
    @Basic
    @Column(name = "blocked")
    private boolean blocked;
    @Basic
    @Column(name = "blockedByOperator")
    private boolean blockedByOperator;
    @Basic
    @Column(name = "balance", nullable = false)
    private float balance;
    @ManyToOne
    @JoinColumn(name = "idTariff")
    private Tariff tariff;
    @ManyToOne
    @JoinColumn(name = "idClient")
    @JsonBackReference
    private Client client;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "`contract_option`",
                joinColumns = @JoinColumn(name = "idContract"),
                inverseJoinColumns = @JoinColumn(name = "idOption"))
    @NotNull
    private List<Option> options;

    public Contract() {

    }

    public Contract(Client client, NumbersPool numbersPool, Tariff tariff, List<Option> options) {
        this.balance = 0;
        this.client = client;
        this.numbersPool = numbersPool;
        this.tariff = tariff;
        this.options = options;
    }

    public Contract(int id, NumbersPool numbersPool, boolean blocked, boolean blockedByOperator, float balance, Tariff tariff, Client client, List<Option> options) {
        this.id = id;
        this.numbersPool = numbersPool;
        this.blocked = blocked;
        this.blockedByOperator = blockedByOperator;
        this.balance = balance;
        this.tariff = tariff;
        this.client = client;
        this.options = options;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public NumbersPool getNumbersPool() {
        return numbersPool;
    }

    public void setNumbersPool(NumbersPool numbersPool) {
        this.numbersPool = numbersPool;
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

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
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
        if (numbersPool != null ? !numbersPool.equals(contract.numbersPool) : contract.numbersPool != null)
            return false;
        if (tariff != null ? !tariff.equals(contract.tariff) : contract.tariff != null) return false;
        if (client != null ? !client.equals(contract.client) : contract.client != null) return false;
        return options != null ? options.equals(contract.options) : contract.options == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (numbersPool != null ? numbersPool.hashCode() : 0);
        result = 31 * result + (blocked ? 1 : 0);
        result = 31 * result + (blockedByOperator ? 1 : 0);
        result = 31 * result + (balance != +0.0f ? Float.floatToIntBits(balance) : 0);
        result = 31 * result + (tariff != null ? tariff.hashCode() : 0);
        result = 31 * result + (client != null ? client.hashCode() : 0);
        result = 31 * result + (options != null ? options.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", numbersPool=" + numbersPool +
                ", blocked=" + blocked +
                ", blockedByOperator=" + blockedByOperator +
                ", balance=" + balance +
                ", tariff=" + tariff +
                ", options=" + options +
                '}';
    }
}