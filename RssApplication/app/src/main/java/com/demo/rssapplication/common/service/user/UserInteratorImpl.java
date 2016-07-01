package com.demo.rssapplication.common.service.user;

import com.demo.rssapplication.application.RssApplication;
import com.demo.rssapplication.common.model.AuthCredentials;
import com.demo.rssapplication.common.model.User;
import com.demo.rssapplication.common.service.base.ApiClient;
import com.demo.rssapplication.common.service.base.NetworkRequest;
import com.demo.rssapplication.common.service.base.OnResponseListener;
import com.demo.rssapplication.common.service.base.ResponseError;

import javax.security.auth.login.LoginException;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Response;
import rx.Observable;
import rx.Subscription;

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
    public Observable<User> doLogin(AuthCredentials credentials) {

        return Observable.just(credentials).flatMap(credentials1 -> {

            try {
                // Simulate network delay
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (credentials1.getEmail().equals("abc@gmail.com") && credentials1.getPassword().equals("123456789")) {
                User user = new User();
                // TODO: will save the object to Database
                return Observable.just(user);
            }

            return Observable.error(new LoginException());
        });
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

        UserApi mUserApi = ApiClient.getClient().create(UserApi.class);
        Subscription getPostSubscription =
                NetworkRequest.performAsyncRequest(mUserApi.createAccount(email, password),
                        listener::onSuccess,
                        throwable -> listener.onFailure(ResponseError.build(throwable)));
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
