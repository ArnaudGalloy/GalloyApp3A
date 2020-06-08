package com.example.galloyapp3a;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WeatherApi {

    @GET("/api/location/44418/")
    Call<RestWeatherResponse> getWeatherResponse();
}
