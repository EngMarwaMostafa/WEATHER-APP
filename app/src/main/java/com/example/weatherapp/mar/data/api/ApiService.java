package com.example.weatherapp.mar.data.api;

import com.example.weatherapp.mar.data.model.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("data/2.5/weather?")

    Call<Example> getCurrentWeatherData(@Query("q") String name) ;

    }


