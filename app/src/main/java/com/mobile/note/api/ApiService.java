package com.mobile.note.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobile.note.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    // http://localhost:8080/api/v1/users

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.42.50:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("users")
    Call<List<User>> getAllUsers();

    @POST("users")
    Call<User> createUser(@Body User user);
}
