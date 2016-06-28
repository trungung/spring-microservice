package com.demo.rssapplication.common.service;

import com.google.firebase.auth.FirebaseUser;

/**
 * The service using for integration Firebase service to app
 */
public interface FirebaseService {

    /**
     * The base interface for FirebaseAuth listener
     */
    interface OnFirebaseAuthListener {

        void onSuccess(FirebaseUser response);

        void onFailure(String error);
    }

    /**
     * Create new user accounts
     *
     * @param email     the user email
     * @param password  the user password
     * @param listener  the response listener
     */
    void createUser(String email, String password, OnFirebaseAuthListener listener);

    /**
     * Authentication to Firebase using email and password
     *
     * @param email     the user email
     * @param password  the user password
     * @param listener  the response listener
     */
    void auth(String email, String password, OnFirebaseAuthListener listener);

    /**
     * SignOut Firebase
     * @param listener  the response listener
     */
    void signOut(OnFirebaseAuthListener listener);
}
