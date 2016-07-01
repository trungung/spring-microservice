package com.demo.rssapplication.activity.signup;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.demo.rssapplication.common.model.AuthCredentials;
import com.demo.rssapplication.common.model.User;
import com.demo.rssapplication.common.service.base.OnResponseListener;
import com.demo.rssapplication.common.service.base.ResponseError;
import com.demo.rssapplication.common.service.user.UserInteratorImpl;
import com.demo.rssapplication.common.utilities.Utils;

import nucleus.presenter.RxPresenter;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SignUpPresenterImpl extends RxPresenter<SignUpFragment> implements SignUpPresenter {

    private SignUpFragment view;
    private Subscriber<User> subscriber;

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
//            if (!KeyboardUtils.keyboardIsShowed(emailEdt))
                this.view.setEmailError();
        }
    }

    @Override
    public void validatePassword(EditText passwordEdt) {
        String passStr = passwordEdt.getText().toString();

        if (TextUtils.isEmpty(passStr) || passStr.length() < 8) {
//            if (!KeyboardUtils.keyboardIsShowed(passwordEdt))
                this.view.setPasswordError();
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
//        this.view.showProgress();
//        UserInteratorImpl.getInstance().login(email, password, new OnResponseListener() {
//            @Override
//            public void onSuccess(Response response) {
//
//            }
//
//            @Override
//            public void onFailure(ResponseError error) {
//
//            }
//        });

        view.showProgress();

        // Kind of "callback"
        cancelSubscription();
        subscriber = new Subscriber<User>() {
            @Override
            public void onCompleted() {
                view.navigateToHome();
            }

            @Override
            public void onError(Throwable e) {
                view.showError();
            }

            @Override
            public void onNext(User user) {
                // Blank
            }
        };

        // do the login
        UserInteratorImpl.getInstance().doLogin(new AuthCredentials(email, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * Cancels any previous callback
     */
    private void cancelSubscription() {
        if (subscriber != null && !subscriber.isUnsubscribed()) {
            subscriber.unsubscribe();
        }
    }
}

