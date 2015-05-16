package me.earlwlkr.cityhotspots;

import org.parceler.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by earl on 5/16/2015.
 */
@Parcel
public class DinerOptions {
    @SerializedName("cuisines")
    List<String> cuisines;

    @SerializedName("districts")
    List<String> districts;

    @SerializedName("categories")
    List<String> categories;

    public List<String> getCuisines() { return cuisines; }
    public List<String> getDistricts() { return districts; }
    public List<String> getCategories() { return categories; }
}
