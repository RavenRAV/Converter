package com.example.ravena3l7.data;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("latest")
    Call<JsonObject>getCuurencies(@Query("access_key") String key);
}
