package com.demo.rssapplication.activity.base;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.airbnb.rxgroups.AutoResubscribe;
import com.airbnb.rxgroups.ResubscriptionObserver;
import com.demo.rssapplication.R;
import com.demo.rssapplication.common.utilities.ViewUtils;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();
    private static final String OBSERVABLE_TAG = "arbitrary_tag";

    protected Toolbar mToolbar;
    protected ActionBar mActionBar;

    // The Observer field must be public, otherwise RxGroups can't access it
    @AutoResubscribe
    public final ResubscriptionObserver<Long> observer = new ResubscriptionObserver<Long>() {
        @Override public void onCompleted() {
            Log.d(TAG, "onCompleted()");
        }

        @Override public void onError(Throwable e) {
            Log.e(TAG, "onError()", e);
        }

        @Override public void onNext(Long l) {
            // output.setText(output.getText() + " " + l);
        }

        @Override public Object resubscriptionTag() {
            return OBSERVABLE_TAG;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void showActionBar(boolean showed) {
        showActionBar(showed, R.drawable.back_menu_icon);
    }

    public void showActionBar(boolean showed, int backIconResId) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            ViewUtils.centerToolbarTitle(mToolbar);
        }

        mActionBar = getSupportActionBar();

        if (mActionBar != null) {
            if (showed) {
                mActionBar.show();
                mActionBar.setDisplayHomeAsUpEnabled(true);
                mActionBar.setHomeButtonEnabled(true);

                Drawable imgBackIcon = ContextCompat.getDrawable(this, backIconResId);
                mActionBar.setHomeAsUpIndicator(imgBackIcon);
            } else {
                mActionBar.hide();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // User touches on Back button.
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
