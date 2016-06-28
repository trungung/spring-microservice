package com.demo.rssapplication.common.service.user;

import com.demo.rssapplication.application.RssApplication;
import com.demo.rssapplication.common.service.base.OnResponseListener;
import com.demo.rssapplication.common.service.base.ResponseError;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Response;

public class UserInteratorImpl implements UserInterator {

    private static UserInterator mInstance = null;

    private Realm mRealm;

    private UserInteratorImpl() {
        RealmConfiguration config = new RealmConfiguration.Builder(RssApplication.getContext()).build();
        mRealm = Realm.getInstance(config);
    }

    /**
     * The singleton to get the UserInterator instance
     *
     * @return {@link UserInterator}
     */
    public static UserInterator getInstance() {

        if (mInstance == null) {
            mInstance = new UserInteratorImpl();
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
