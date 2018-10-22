package com.example.darko.topnews;

import com.example.darko.topnews.model.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface NewsAPI {

    String apiKey = "7c0efca3532f4d748f6856e6572a386f";
    String country = "gb";

    @Headers("Content-Type: application/json")
    @GET("top-headlines")
    Call<News> getNews(@Query("country") String country, @Query("apiKey") String apiKey);

}
