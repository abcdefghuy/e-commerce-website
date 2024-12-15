package org.example.ecommercewebsite.business;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long addressID;
    private String street;
    private String city;
    private String province;
    private String country;

    public Address() {
    }

    public Address(String street, String city, String province, String country) {
        this.street = street;
        this.city = city;
        this.province = province;
        this.country = country;
    }


    public Long getAddressID() {
        return addressID;
    }

    public void setAddressID(Long addressID) {
        this.addressID = addressID;
    }

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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress()
    {
        return street + ", " + city + ", " + province + ", " + country;
    }

    public boolean isComplete() {
        return street != null && !street.isEmpty() &&
                city != null && !city.isEmpty() &&
                province != null && !province.isEmpty() &&
                country != null && !country.isEmpty();
    }
}
