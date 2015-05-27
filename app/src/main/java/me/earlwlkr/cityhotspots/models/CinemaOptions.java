package me.earlwlkr.cityhotspots.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by earl on 5/16/2015.
 */
@Parcel
public class CinemaOptions {

    private List<String> trademarks;

    public List<String> getTrademarks() {
        return trademarks;
    }

    public void setTrademarks(List<String> districts) {
        this.trademarks = trademarks;
    }
}
