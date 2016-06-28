package com.demo.rssapplication.common.service.user;

import com.demo.rssapplication.common.service.base.OnResponseListener;

/**
 * The SignUp Interactor interface
 */
public interface UserInterator {

    /**
     * SignUp With email and password
     *
     * @param email the valid email
     * @param password the valid password
     * @param listener the response listener
     */
    void signUp(String email, String password, OnResponseListener listener);

    /**
     * Login With email and password
     *
     * @param email the valid email
     * @param password the valid password
     * @param listener the response listener
     */
    void login(String email, String password, OnResponseListener listener);
}
