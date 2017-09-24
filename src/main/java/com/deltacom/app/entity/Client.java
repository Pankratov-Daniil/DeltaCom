package com.deltacom.app.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.Date;

public class Client {
    private Integer id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String passport;
    private String address;
    private Set<Contract> contracts;
    private String email;
    private String password;

    public void setId (Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setFirstName (String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getPassport() {
        return passport;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setContracts(Set<Contract> contracts) {
        this.contracts = (contracts == null) ? new HashSet<Contract>() : new HashSet<Contract>(contracts);
    }

    public Set<Contract> getContracts() {
        return (contracts == null) ? new HashSet<Contract>() : new HashSet<Contract>(contracts);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;

        result = prime * result + id.hashCode();
        result = prime * result + firstName.hashCode();
        result = prime * result + lastName.hashCode();
        result = prime * result + passport.hashCode();
        result = prime * result + address.hashCode();
        result = prime * result + email.hashCode();
        result = prime * result + password.hashCode();

        return result;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj)
            return true;
        if(!(obj instanceof Client))
            return false;
        Client anotherClient = (Client)obj;

        if((this.contracts != null && anotherClient.contracts == null) ||
                (this.contracts == null && anotherClient.contracts != null) ||
                this.contracts.size() != anotherClient.contracts.size())
            return false;

        return anotherClient.id.compareTo(this.id) == 0 &&
                anotherClient.firstName.equals(this.firstName) &&
                anotherClient.lastName.equals(this.lastName) &&
                anotherClient.passport.equals(this.passport) &&
                anotherClient.address.equals(this.address) &&
                anotherClient.email.equals(this.email) &&
                anotherClient.password.equals(this.password) &&
                anotherClient.birthDate.equals(this.birthDate) &&
                this.contracts.containsAll(anotherClient.contracts) &&
                anotherClient.contracts.containsAll(this.contracts);
    }
}