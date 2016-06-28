package com.demo.rssapplication.activity.signup;

import com.demo.rssapplication.application.RssApplication;
import com.demo.rssapplication.common.service.OnResponseListener;
import com.demo.rssapplication.common.service.ResponseError;
import com.demo.rssapplication.common.service.UserServiceImpl;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Response;

public class SignUpInteratorImpl implements SignUpInterator {

    private static SignUpInterator mInstance = null;

    private Realm mRealm;

    private SignUpInteratorImpl() {
        RealmConfiguration config = new RealmConfiguration.Builder(RssApplication.getContext()).build();
        mRealm = Realm.getInstance(config);
    }

    /**
     * The singleton to get the SignUpInterator instance
     *
     * @return {@link SignUpInterator}
     */
    public static SignUpInterator getInstance() {

        if (mInstance == null) {
            mInstance = new SignUpInteratorImpl();
        }

        return mInstance;
    }

    @Override
    public void signUp(String email, String password, final OnResponseListener listener) {
        UserServiceImpl.getInstance().signUp(email, password, new OnResponseListener() {
            @Override
            public void onSuccess(Response response) {
                listener.onSuccess(response);
            }

            @Override
            public void onFailure(ResponseError error) {
                listener.onFailure(error);
            }
        });
    }

    @Override
    public void login(String email, String password, final OnResponseListener listener) {
        UserServiceImpl.getInstance().login(email, password, new OnResponseListener() {
            @Override
            public void onSuccess(Response response) {
                listener.onSuccess(response);
            }

            @Override
            public void onFailure(ResponseError error) {
                listener.onFailure(error);
            }
        });
    }
}
