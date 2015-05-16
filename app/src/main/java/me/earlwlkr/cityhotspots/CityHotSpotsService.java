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
    void getDiners(Callback<List<Diner>> diners);

    @GET("/dineroptions")
    void getDinerOptions(Callback<List<DinerOptions>> diners);
}
