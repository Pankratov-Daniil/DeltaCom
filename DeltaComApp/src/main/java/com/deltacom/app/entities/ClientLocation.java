package com.deltacom.app.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Date;

/**
 * Class for clients locations
 */
@Entity
@Table(name = "client_location")
public class ClientLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonBackReference
    private Client client;
    @Basic
    @Column(name = "latitude", nullable = false)
    private float latitude;
    @Basic
    @Column(name = "longitude", nullable = false)
    private float longitude;
    @Basic
    @Column(name = "city", nullable = false)
    @NotBlank
    private String city;
    @Basic
    @Column(name = "country", nullable = false)
    @NotBlank
    private String country;
    @Basic
    @Column(name = "ip_address", nullable = false)
    @NotBlank
    private String ipAddress;
    @Basic
    @Column(name = "entered_date", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    //@DateTimeFormat(pattern = "dd.MM.yyyy'T'hh:mm")
    private Date enteredDate;

    public ClientLocation() {

    }

    public ClientLocation(int id, float latitude, float longitude, String city, String country, String ipAddress, Date enteredDate) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.country = country;
        this.ipAddress = ipAddress;
        this.enteredDate = enteredDate;
    }

    public ClientLocation(Client client, float latitude, float longitude, String city, String country, String ipAddress, Date enteredDate) {
        this.client = client;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.country = country;
        this.ipAddress = ipAddress;
        this.enteredDate = enteredDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Date getEnteredDate() {
        return enteredDate;
    }

    public void setEnteredDate(Date enteredDate) {
        this.enteredDate = enteredDate;
    }

    @Override
    public String toString() {
        return "ClientLocation{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", enteredDate=" + enteredDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientLocation that = (ClientLocation) o;

        if (id != that.id) return false;
        if (Float.compare(that.latitude, latitude) != 0) return false;
        if (Float.compare(that.longitude, longitude) != 0) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (ipAddress != null ? !ipAddress.equals(that.ipAddress) : that.ipAddress != null) return false;
        return enteredDate != null ? enteredDate.equals(that.enteredDate) : that.enteredDate == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (Float.compare(latitude, 0.0f) != 0 ? Float.floatToIntBits(latitude) : 0);
        result = 31 * result + (Float.compare(longitude, 0.0f) != 0 ? Float.floatToIntBits(longitude) : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (ipAddress != null ? ipAddress.hashCode() : 0);
        result = 31 * result + (enteredDate != null ? enteredDate.hashCode() : 0);
        return result;
    }
}
