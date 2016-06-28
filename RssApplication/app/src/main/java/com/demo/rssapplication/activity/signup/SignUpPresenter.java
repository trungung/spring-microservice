package com.demo.rssapplication.activity.signup;

import android.widget.EditText;

public interface SignUpPresenter {

    /**
     * Validate email and password
     *
     * @param email the email
     * @param password the password
     * @return valid or not
     */
    boolean validateForm(String email, String password);

    /**
     * Validate Email EditText
     *
     * @param email edittext email
     */
    void validateEmail(EditText email);

    /**
     * Validate Password EditText
     *
     * @param password edittext password
     */
    void validatePassword(EditText password);

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
