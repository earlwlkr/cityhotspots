package me.earlwlkr.cityhotspots.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by earl on 5/14/2015.
 */
public class RestClient
{
    private static RestClient mInstance = null;

    private static final String BASE_URL = "http://cityhotspots-46171.onmodulus.net";
    private CityHotSpotsService apiService;

    protected RestClient()
    {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        apiService = restAdapter.create(CityHotSpotsService.class);
    }

    public static synchronized RestClient getInstance(){
        if(null == mInstance){
            mInstance = new RestClient();
        }
        return mInstance;
    }

    public CityHotSpotsService getApiService()
    {
        return apiService;
    }
}
