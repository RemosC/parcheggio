package org.remo.models;

import javax.persistence.*;

@Entity
@Table(name="addresses")
public class Address extends BaseModel {

    private String street;
    private String city;
    @Column(name="street_number")
    private String streetNumber;
    @Column(name="postal_code")
    private String postalCode;
    private Long lat;
    private Long lang;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Long getLat() {
        return lat;
    }

    public void setLat(Long lat) {
        this.lat = lat;
    }

    public Long getLang() {
        return lang;
    }

    public void setLang(Long lang) {
        this.lang = lang;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
