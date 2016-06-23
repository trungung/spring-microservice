package com.demo.rssapplication.activity.signup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.demo.rssapplication.R;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.OnClick;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusFragment;

/**
 * A placeholder fragment containing a simple view.
 */
@RequiresPresenter(SignUpPresenterImpl.class)
public class SignUpFragment extends NucleusFragment<SignUpPresenterImpl> implements SignUpView, View.OnClickListener {

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.edt_email)
    EditText mEmailField;

    @BindView(R.id.edt_password)
    EditText mPasswordField;

    private static final String TAG = SignUpFragment.class.getSimpleName();

    public SignUpFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @OnClick(R.id.btn_signup)
    public void signUpAction() {

    }

    @OnClick(R.id.btn_login)
    public void loginAction() {

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setEmailError() {
        mEmailField.setError(getString(R.string.email_error));
    }

    @Override
    public void setPasswordError() {
        mPasswordField.setError(getString(R.string.pass_error));
    }

    @Override
    public void navigateToHome() {

    }

    @Override
    public void singnUp(String email, String pass) {

    }

    @Override
    public void login(String email, String pass) {

    }


    @Override
    public void onClick(View view) {
        getPresenter().validateForm(mEmailField.getText().toString(), mPasswordField.getText().toString());
    }

    private void updateUI(FirebaseUser user) {
//        hideProgressDialog();
        if (user != null) {
        } else {
        }
    }
}
