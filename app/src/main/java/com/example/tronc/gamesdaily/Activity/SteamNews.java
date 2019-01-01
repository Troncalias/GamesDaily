package com.example.tronc.gamesdaily.Activity;

import com.example.tronc.gamesdaily.Models.AppNewsContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SteamNews {

    @GET(".")
    Call<AppNewsContainer> getListOfNews(@Query("appid") int appId, @Query("count") int count, @Query("max") int maxLenght);
}
