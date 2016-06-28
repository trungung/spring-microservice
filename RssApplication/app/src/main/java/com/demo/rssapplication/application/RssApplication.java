package com.demo.rssapplication.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;

public class RssApplication extends Application implements Application.ActivityLifecycleCallbacks {

    private static final String TAG = RssApplication.class.getSimpleName();

    private static SharedPreferences sSharedPreferences;
    private static SharedPreferences.Editor sEditor;
    private static Context sContext;
    private static Activity sActivity;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "on App create");

        sContext = getApplicationContext();
        sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(sContext);
        sEditor = sSharedPreferences.edit();

        registerActivityLifecycleCallbacks(this);

        LeakCanary.install(this);
    }

    public static Context getContext() {
        return sContext;
    }

    public static Activity getCurrentActivity() {
        return sActivity;
    }

    public static SharedPreferences getSharedPreferences() {
        return sSharedPreferences;
    }

    public static SharedPreferences.Editor getEditor() {
        return sEditor;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        sActivity = activity;
        Log.d(TAG, "onActivityCreated: " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
