package me.earlwlkr.cityhotspots.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Created by earl on 5/14/2015.
 */
@Parcel
public class Cinema extends Place {
    @SerializedName("foody_id")
    private int foodyId;

    @SerializedName("phone")
    private String phoneNumber;

    public int getFoodyId() {
        return foodyId;
    }

    public void setFoodyId(int foodyId) {
        this.foodyId = foodyId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
