package com.demo.rssapplication.common.service.base;

import com.demo.rssapplication.application.RssApplication;
import com.demo.rssapplication.common.model.User;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class RealmService {

    public interface OnTransactionCallback {
        void onRealmSuccess();
        void onRealmError(final Throwable e);
    }

    private final Realm mRealm;
    private static RealmService mInstance = null;

    private RealmService() {
        RealmConfiguration config = new RealmConfiguration.Builder(RssApplication.getContext()).build();
        mRealm = Realm.getInstance(config);
    }

    /**
     * The singleton to get the RealmService instance
     *
     * @return {@link RealmService}
     */
    public static RealmService getInstance() {

        if (mInstance == null) {
            mInstance = new RealmService();
        }

        return mInstance;
    }

    public void closeRealm() {
        mRealm.close();
    }

    // Other methods
    public RealmResults<User> getAllUsers() {
        return mRealm.where(User.class).findAll();
    }

    public void addUserAsync(final String firstName, final String lastName, final String email,
                             final OnTransactionCallback onTransactionCallback) {

        mRealm.executeTransactionAsync(realm -> {
            User user = realm.createObject(User.class);

            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
        }, () -> {
            if (onTransactionCallback != null)
                onTransactionCallback.onRealmSuccess();
        }, error -> {
            if (onTransactionCallback != null)
                onTransactionCallback.onRealmError(error);
        });}
}
