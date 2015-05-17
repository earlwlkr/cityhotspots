package me.earlwlkr.cityhotspots;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by earl on 5/16/2015.
 */
public class DinerOptions {
    @SerializedName("cuisines")
    private List<String> cuisines;

    @SerializedName("districts")
    private List<String> districts;

    @SerializedName("categories")
    private List<String> categories;

    public List<String> getCuisines() { return cuisines; }
    public List<String> getDistricts() { return districts; }
    public List<String> getCategories() { return categories; }
}
