package com.deltacom.app.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Class for mobile operators client.
 */
@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "firstName", nullable = false)
    @NotBlank
    private String firstName;
    @Basic
    @Column(name = "lastName", nullable = false)
    @NotBlank
    private String lastName;
    @Basic
    @Column(name = "birthDate", nullable = false)
    @Temporal(value = TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date birthDate;
    @Basic
    @Column(name = "passport", nullable = false)
    @NotBlank
    private String passport;
    @Basic
    @Column(name = "address", nullable = false)
    @NotBlank
    private String address;
    @Basic
    @Column(name = "email", nullable = false)
    @NotBlank
    private String email;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "activated")
    private boolean isActivated;
    @Basic
    @Column(name = "forgottenPassToken")
    private String forgottenPassToken;
    @Basic
    @Column(name = "openIdToken")
    private String openIdToken;
    @Basic
    @Column(name = "twoFactorAuth")
    private boolean usingTwoFactorAuth;
    @Basic
    @Column(name = "smsCode")
    private String smsCode;
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @JoinTable(name = "`clients_access_levels`",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "access_level_id"))
    private Set<AccessLevel> accessLevels;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "client", orphanRemoval = true)
    @JsonManagedReference
    private Set<Contract> contracts;

    public Client() {

    }

    public Client(int id, String firstName, String lastName, Date birthDate, String passport, String address, String email,
                  String password, boolean isActivated, String forgottenPassToken, String openIdToken,
                  boolean usingTwoFactorAuth, String smsCode, Set<AccessLevel> accessLevels, Set<Contract> contracts) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.passport = passport;
        this.address = address;
        this.email = email;
        this.password = password;
        this.isActivated = isActivated;
        this.forgottenPassToken = forgottenPassToken;
        this.openIdToken = openIdToken;
        this.usingTwoFactorAuth = usingTwoFactorAuth;
        this.smsCode = smsCode;
        this.accessLevels = accessLevels;
        this.contracts = contracts;
    }

    public Client(int id, String firstName, String lastName, Date birthDate, String passport, String address, String email,
                  String password, boolean isActivated, String forgottenPassToken, String openIdToken, Set<AccessLevel> accessLevels, Set<Contract> contracts) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.passport = passport;
        this.address = address;
        this.email = email;
        this.password = password;
        this.isActivated = isActivated;
        this.forgottenPassToken = forgottenPassToken;
        this.openIdToken = openIdToken;
        this.accessLevels = accessLevels;
        this.contracts = contracts;
    }

    public Client(int id, String firstName, String lastName, Date birthDate, String passport, String address,
                  String email, String password, Set<AccessLevel> accessLevels, Set<Contract> contracts) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.passport = passport;
        this.address = address;
        this.email = email;
        this.password = password;
        this.accessLevels = accessLevels;
        this.contracts = contracts;
    }

    public Client(String firstName, String lastName, Date birthDate, String passport, String address, String email,
                  String password, Set<AccessLevel> accessLevels) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.passport = passport;
        this.address = address;
        this.email = email;
        this.password = password;
        this.accessLevels = accessLevels;
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

    public boolean getTwoFactorAuth() {
        return usingTwoFactorAuth;
    }

    public void setTwoFactorAuth(boolean twoFactorAuth) {
        usingTwoFactorAuth = twoFactorAuth;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public Set<AccessLevel> getAccessLevels() {
        return accessLevels;
    }

    public void setAccessLevels(Set<AccessLevel> accessLevel) {
        this.accessLevels = accessLevel;
    }

    public Set<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(Set<Contract> contracts) {
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
                ", isActivated='" + isActivated + '\'' +
                ", forgottenPassToken='" + forgottenPassToken + '\'' +
                ", openIdToken='" + openIdToken + '\'' +
                ", accessLevels=" + accessLevels +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (id != client.id) return false;
        if (isActivated != client.isActivated) return false;
        if (firstName != null ? !firstName.equals(client.firstName) : client.firstName != null) return false;
        if (lastName != null ? !lastName.equals(client.lastName) : client.lastName != null) return false;
        if (birthDate != null ? !birthDate.equals(client.birthDate) : client.birthDate != null) return false;
        if (passport != null ? !passport.equals(client.passport) : client.passport != null) return false;
        if (address != null ? !address.equals(client.address) : client.address != null) return false;
        if (email != null ? !email.equals(client.email) : client.email != null) return false;
        if (password != null ? !password.equals(client.password) : client.password != null) return false;
        if (forgottenPassToken != null ? !forgottenPassToken.equals(client.forgottenPassToken) : client.forgottenPassToken != null)
            return false;
        if (openIdToken != null ? !openIdToken.equals(client.openIdToken) : client.openIdToken != null) return false;
        return accessLevels != null ? !accessLevels.equals(client.accessLevels) : client.accessLevels != null;
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
        result = 31 * result + (isActivated ? 1 : 0);
        result = 31 * result + (forgottenPassToken != null ? forgottenPassToken.hashCode() : 0);
        result = 31 * result + (openIdToken != null ? openIdToken.hashCode() : 0);
        result = 31 * result + (accessLevels != null ? accessLevels.hashCode() : 0);
        return result;
    }
}