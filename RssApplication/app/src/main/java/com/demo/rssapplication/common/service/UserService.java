package com.demo.rssapplication.common.service;

import com.demo.rssapplication.common.model.User;

import retrofit2.http.POST;
import rx.Observable;

public interface UserService {

    @POST("/me")
    Observable<User> me();

//    /**
//     * User SignOut Firebase
//     */
//    void signOut();
//
//    /**
//     * SignIn Firebase account with email and password
//     * @param email email
//     * @param password pass
//     */
//    void signIn(String email, String password);
//
//    /**
//     * Register Firebase account with email and password
//     * @param email email
//     * @param password pass
//     */
//    void createAccount(String email, String password);
}
