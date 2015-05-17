package me.earlwlkr.cityhotspots;

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

    public String getDistrict() {
        return district;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }
}
