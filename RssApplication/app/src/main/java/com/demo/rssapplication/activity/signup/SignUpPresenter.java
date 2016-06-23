package com.demo.rssapplication.activity.signup;

public interface SignUpPresenter {

    boolean validateForm(String email, String password);

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
