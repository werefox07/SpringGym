package ru.zaharova.oxana.gym.rest;

import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.zaharova.oxana.gym.rest.entites.WeatherRequestRestModel;

public interface IOpenWeather {
    @NonNull
    @GET("data/2.5/weather")
    Call<WeatherRequestRestModel> loadWeather(@Query("q") @NonNull String city,
                                              @Query("appid") @NonNull String keyApi,
                                              @Query("units") @NonNull String units);
}
