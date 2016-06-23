package com.demo.rssapplication.activity.signup;

/**
 * The SignUp Interactor interface
 */
public interface SignUpInterator {

    /**
     * SignUp With email and password
     *
     * @param email the valid email
     * @param password the valid password
     */
    void signUp(String email, String password);

    /**
     * Login With email and password
     *
     * @param email the valid email
     * @param password the valid password
     */
    void login(String email, String password);
}
