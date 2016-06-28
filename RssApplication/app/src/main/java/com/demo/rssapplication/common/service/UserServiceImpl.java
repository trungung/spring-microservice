package com.demo.rssapplication.common.service;

import com.google.firebase.auth.FirebaseUser;

import retrofit2.Response;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * This class provides APIs use to interact with User model
 */
public class UserServiceImpl implements UserService {

    private static UserService mInstance = null;
    private static UserApi mUserApi;

    private UserServiceImpl() {
        // Create User API with Retrofit Networking
        mUserApi = ApiClient.getClient().create(UserApi.class);
    }

    /**
     * The singleton to get the UserService instance
     *
     * @return {@link UserService}
     */
    public static UserService getInstance() {

        if (mInstance == null) {
            mInstance = new UserServiceImpl();
        }

        return mInstance;
    }

    @Override
    public void signUp(String email, String password, final OnResponseListener listener) {

        mUserApi.createAccount(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(ResponseError.build(e));
                    }

                    @Override
                    public void onNext(Response response) {
                        listener.onSuccess(response);
                    }
                });

        FirebaseServiceImpl.getInstance().createUser(email, password, new FirebaseService.OnFirebaseAuthListener() {
            @Override
            public void onSuccess(FirebaseUser response) {

            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    @Override
    public void login(String email, String password, final OnResponseListener listener) {

        mUserApi.login(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(ResponseError.build(e));
                    }

                    @Override
                    public void onNext(Response response) {
                        listener.onSuccess(response);
                    }
                });

        FirebaseServiceImpl.getInstance().auth(email, password, new FirebaseService.OnFirebaseAuthListener() {
            @Override
            public void onSuccess(FirebaseUser response) {

            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
}
