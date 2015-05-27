package me.earlwlkr.cityhotspots.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Created by earl on 5/14/2015.
 */
@Parcel
public class Cinema {
    @SerializedName("foody_id")
    private int foodyId;

    private String name;

    @SerializedName("phone")
    private String phoneNumber;

    private Address address;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
