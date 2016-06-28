package com.demo.rssapplication.activity.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.demo.rssapplication.R;
import com.demo.rssapplication.activity.example.ListExampleActivity;
import com.google.firebase.auth.FirebaseUser;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusFragment;
import rx.functions.Action1;

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

    private Unbinder unbinder;
    
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
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        unbinder = ButterKnife.bind(this, view);

        final SignUpPresenterImpl presenter = getPresenter();
        RxTextView.textChanges(mEmailField)
                .subscribe(new Action1<CharSequence>() {
                    @Override
                    public void call(CharSequence charSequence) {
                        presenter.validateForm(mEmailField.getText().toString(), mPasswordField.getText().toString());
                    }
                });

        RxTextView.textChanges(mPasswordField)
                .subscribe(new Action1<CharSequence>() {
                    @Override
                    public void call(CharSequence charSequence) {
                        presenter.validateForm(mEmailField.getText().toString(), mPasswordField.getText().toString());
                    }
                });

        return view;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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

        Intent intent = new Intent(getActivity(), ListExampleActivity.class);
        startActivity(intent);

//        getPresenter().signUp(mEmailField.getText().toString(), mPasswordField.getText().toString());
    }

    @OnClick(R.id.btn_login)
    public void loginAction() {
        getPresenter().login(mEmailField.getText().toString(), mPasswordField.getText().toString());
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
