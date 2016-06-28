package com.demo.rssapplication.common.service;

import com.demo.rssapplication.common.model.User;

import retrofit2.Response;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface UserApi {

    @POST("/me")
    Observable<User> me();

    @POST("/signup")
    Observable<Response> createAccount(@Query("email") String email, @Query("password") String password);

    @POST("/login")
    Observable<Response> login(@Query("email") String email, @Query("password") String password);
}
