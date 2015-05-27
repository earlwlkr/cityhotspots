package me.earlwlkr.cityhotspots.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Created by earl on 5/14/2015.
 */
@Parcel
public class Diner extends Place {
    @SerializedName("foody_id")
    private int foodyId;

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

    private LatLng position;

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    public int getFoodyId() {
        return foodyId;
    }

    public void setFoodyId(int foodyId) {
        this.foodyId = foodyId;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public int getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(int priceMin) {
        this.priceMin = priceMin;
    }

    public int getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(int priceMax) {
        this.priceMax = priceMax;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }
}
