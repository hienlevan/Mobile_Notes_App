package com.mobile.note.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobile.note.model.Note;
import com.mobile.note.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    // http://localhost:8080/api/v1/users

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.43.58:8080/rest/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("user")
    Call<List<User>> getAllUsers();

    @POST("user")
    Call<User> createUser(@Body User user);

    @GET("user/{id}")
    Call<User> getUserById(@Path("id") int id);

    @PUT("user/{id}")
    Call<User> updateUser(@Path("id") int id, @Body User user);

    @DELETE("user/{id}")
    Call<User> deleteUser(@Path("id") int id);

    @POST("note/{userId}/note")
    Call<Note> createNote(@Path("userId") int userId, @Body Note note);

    @GET("note/{userId}/notes")
    Call<List<Note>> getListOfNotes(@Path("userId") int userId);

    @PUT("note/{noteId}")
    Call<Note> updateNote(@Path("noteId") int noteId, @Body Note note);

    @DELETE("note/{noteId}")
    Call<Note> deteleNote(@Path("noteId") int noteId);
}
