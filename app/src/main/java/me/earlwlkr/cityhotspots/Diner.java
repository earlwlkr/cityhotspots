package me.earlwlkr.cityhotspots;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Created by earl on 5/14/2015.
 */
@Parcel
public class Diner {
    @SerializedName("foody_id")
    private int foodyId;

    private String name;

    @SerializedName("phone")
    private String phoneNumber;

    private String cuisine;

    @SerializedName("price_min")
    private int priceMin;

    @SerializedName("price_max")
    private int priceMax;

    @SerializedName("open_time")
    private Date openTime;

    @SerializedName("close_time")
    private Date closeTime;

    private Address address;

    public int getFoodyId() {
        return foodyId;
    }

    public String getName() {
        return name;
    }

    public String getCuisine() {
        return cuisine;
    }

    public int getPriceMin() {
        return priceMin;
    }

    public int getPriceMax() {
        return priceMax;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public Date getOpenTime() {
        return openTime;
    }
}
