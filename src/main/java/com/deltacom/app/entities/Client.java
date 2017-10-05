package com.deltacom.app.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.List;
import java.util.Date;

/**
 * Class for mobile operators client.
 */
@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "firstName")
    private String firstName;
    @Basic
    @Column(name = "lastName")
    private String lastName;
    @Basic
    @Column(name = "birthDate")
    @Temporal(value = TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date birthDate;
    @Basic
    @Column(name = "passport")
    private String passport;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "password")
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "`clients_access_levels`",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "access_level_id"))
    private List<AccessLevel> accessLevels;
    @OneToMany(mappedBy = "client")
    private List<Contract> contracts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<AccessLevel> getAccessLevels() {
        return accessLevels;
    }

    public void setAccessLevels(List<AccessLevel> accessLevel) {
        this.accessLevels = accessLevel;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", passport='" + passport + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", accessLevels=" + accessLevels +
                ", contracts=" + contracts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (id != client.id) return false;
        if (firstName != null ? !firstName.equals(client.firstName) : client.firstName != null) return false;
        if (lastName != null ? !lastName.equals(client.lastName) : client.lastName != null) return false;
        if (birthDate != null ? !birthDate.equals(client.birthDate) : client.birthDate != null) return false;
        if (passport != null ? !passport.equals(client.passport) : client.passport != null) return false;
        if (address != null ? !address.equals(client.address) : client.address != null) return false;
        if (email != null ? !email.equals(client.email) : client.email != null) return false;
        if (password != null ? !password.equals(client.password) : client.password != null) return false;
        if (accessLevels != null ? !accessLevels.equals(client.accessLevels) : client.accessLevels != null)
            return false;
        return contracts != null ? contracts.equals(client.contracts) : client.contracts == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (passport != null ? passport.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (accessLevels != null ? accessLevels.hashCode() : 0);
        result = 31 * result + (contracts != null ? contracts.hashCode() : 0);
        return result;
    }
}