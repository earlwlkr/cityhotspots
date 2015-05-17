package me.earlwlkr.cityhotspots;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by earl on 5/14/2015.
 */
public interface  CityHotSpotsService {
    @GET("/diners")
    void getDiners(@Query("cuisine") String cuisine,
                   @Query("district") String district,
                   @Query("category") String category,
                   @Query("price_min") String price_min,
                   @Query("price_max") String price_max,
                   @Query("time_arrival") String timeOfArrival,
                   Callback<List<Diner>> diners);

    @GET("/dineroptions")
    void getDinerOptions(Callback<DinerOptions> diners);
}
