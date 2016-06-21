package com.demo.rssapplication.activity.signup;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.demo.rssapplication.R;
import com.demo.rssapplication.activity.base.BaseActivity;

public class SignUpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
