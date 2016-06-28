package com.demo.rssapplication.activity.signup;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.EditText;

import com.demo.rssapplication.R;
import com.demo.rssapplication.application.RssApplication;
import com.demo.rssapplication.common.service.base.OnResponseListener;
import com.demo.rssapplication.common.service.base.ResponseError;
import com.demo.rssapplication.common.service.user.UserInteratorImpl;
import com.demo.rssapplication.common.utilities.Utils;

import nucleus.presenter.RxPresenter;
import retrofit2.Response;

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
    public void validateEmail(EditText emailEdt) {
        String emailStr = emailEdt.getText().toString();

        if (TextUtils.isEmpty(emailStr) || !Utils.isValidEmail(emailStr)) {
            emailEdt.setBackgroundColor(ContextCompat.getColor(RssApplication.getContext(), R.color.colorBgInValid));
        } else {
            emailEdt.setBackgroundColor(ContextCompat.getColor(RssApplication.getContext(), R.color.colorBgValid));
        }
    }

    @Override
    public void validatePassword(EditText passwordEdt) {
        String passStr = passwordEdt.getText().toString();

        if (TextUtils.isEmpty(passStr) || passStr.length() < 8) {
            passwordEdt.setBackgroundColor(ContextCompat.getColor(RssApplication.getContext(), R.color.colorBgInValid));
        } else {
            passwordEdt.setBackgroundColor(ContextCompat.getColor(RssApplication.getContext(), R.color.colorBgValid));
        }
    }

    @Override
    public void signUp(String email, String password) {
        this.view.showProgress();
        UserInteratorImpl.getInstance().signUp(email, password, new OnResponseListener() {
            @Override
            public void onSuccess(Response response) {

            }

            @Override
            public void onFailure(ResponseError error) {

            }
        });
    }

    @Override
    public void login(String email, String password) {
        this.view.showProgress();
        UserInteratorImpl.getInstance().login(email, password, new OnResponseListener() {
            @Override
            public void onSuccess(Response response) {

            }

            @Override
            public void onFailure(ResponseError error) {

            }
        });
    }
}

