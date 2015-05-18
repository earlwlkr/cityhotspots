package me.earlwlkr.cityhotspots.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by earl on 5/16/2015.
 */
@Parcel
public class DinerOptions {
    @SerializedName("cuisines")
    private List<String> cuisines;

    @SerializedName("districts")
    private List<String> districts;

    @SerializedName("categories")
    private List<String> categories;

    @SerializedName("price_min")
    private int priceMin;

    @SerializedName("price_max")
    private int priceMax;

    public List<String> getCuisines() { return cuisines; }
    public List<String> getDistricts() { return districts; }
    public List<String> getCategories() { return categories; }
    public int getPriceMin() { return priceMin; }
    public int getPriceMax() { return priceMax; }

    public void setCuisines(List<String> cuisines) {
        this.cuisines = cuisines;
    }

    public void setDistricts(List<String> districts) {
        this.districts = districts;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public void setPriceMin(int priceMin) {
        this.priceMin = priceMin;
    }

    public void setPriceMax(int priceMax) {
        this.priceMax = priceMax;
    }
}
