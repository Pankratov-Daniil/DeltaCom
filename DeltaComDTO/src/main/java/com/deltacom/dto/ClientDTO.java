package com.deltacom.dto;

import java.util.Arrays;
import java.util.Date;

/**
 * Class for Client Data-Transfer Object
 */
public class ClientDTO {
    private int id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String passport;
    private String address;
    private String email;
    private String password;
    private String[] accessLevels;
    private boolean isActivated;
    private String forgottenPassToken;
    private String openIdToken;

    public ClientDTO() {

    }

    public ClientDTO(int id, String firstName, String lastName, Date birthDate, String passport, String address,
                     String email, String password, String[] accessLevels, boolean isActivated, String forgottenPassToken, String openIdToken) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.passport = passport;
        this.address = address;
        this.email = email;
        this.password = password;
        this.accessLevels = accessLevels;
        this.isActivated = isActivated;
        this.forgottenPassToken = forgottenPassToken;
        this.openIdToken = openIdToken;
    }

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

    public String[] getAccessLevels() {
        return accessLevels;
    }

    public void setAccessLevels(String[] accessLevels) {
        this.accessLevels = accessLevels;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public String getForgottenPassToken() {
        return forgottenPassToken;
    }

    public void setForgottenPassToken(String forgottenPassToken) {
        this.forgottenPassToken = forgottenPassToken;
    }

    public String getOpenIdToken() {
        return openIdToken;
    }

    public void setOpenIdToken(String openIdToken) {
        this.openIdToken = openIdToken;
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", passport='" + passport + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", accessLevels=" + Arrays.toString(accessLevels) +
                ", isActivated=" + isActivated +
                ", forgottenPassToken='" + forgottenPassToken + '\'' +
                ", openIdToken='" + openIdToken + '\'' +
                '}';
    }
}
