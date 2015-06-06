package me.earlwlkr.cityhotspots.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by earl on 5/17/2015.
 */
@Parcel
public class Address {
    @SerializedName("street_address")
    private String streetAddress;
    private String district;
    private String city;
    private String country;

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public String getAddressString() {
        return streetAddress + ", " + district + ", " + city;
    }
}
