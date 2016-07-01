package com.demo.rssapplication.activity.signup;

public interface SignUpView {

    void showProgress();

    void hideProgress();

    void setEmailError();

    void setPasswordError();

    void navigateToHome();

    void showError();

    void singnUp(String email, String pass);

    void login(String email, String pass);
}
