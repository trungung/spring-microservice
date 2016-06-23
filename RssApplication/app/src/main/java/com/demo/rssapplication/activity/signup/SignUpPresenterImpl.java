package com.demo.rssapplication.activity.signup;

import android.os.Bundle;
import android.text.TextUtils;

import com.demo.rssapplication.common.utilities.Utils;

import nucleus.presenter.RxPresenter;

public class SignUpPresenterImpl extends RxPresenter<SignUpFragment> implements SignUpPresenter {

    private SignUpFragment view;


    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
    }

    @Override
    protected void onTakeView(SignUpFragment signUpFragment) {
        super.onTakeView(signUpFragment);

        this.view = signUpFragment;
    }

    @Override
    public boolean validateForm(String email, String password) {
        if (TextUtils.isEmpty(email) && !Utils.isValidEmail(email)) {
            this.view.setEmailError();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            this.view.setPasswordError();
            return false;
        }

        return true;
    }

    @Override
    public void signUp(String email, String password) {

    }

    @Override
    public void login(String email, String password) {

    }
}

