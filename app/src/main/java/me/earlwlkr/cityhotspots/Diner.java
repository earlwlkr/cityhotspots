package me.earlwlkr.cityhotspots;

import android.location.Address;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Created by earl on 5/14/2015.
 */
@Parcel
public class Diner {
    @SerializedName("foody_id")
    int foodyId;
    String name;
    @SerializedName("phone")
    String phoneNumber;
    String cuisine;
    @SerializedName("price_min")
    int priceMin;
    @SerializedName("price_max")
    int priceMax;
    @SerializedName("open_time")
    Date openTime;
    @SerializedName("close_time")
    Date closeTime;
    Address address;
}
