package com.demo.rssapplication.activity.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.demo.rssapplication.R;
import com.demo.rssapplication.activity.example.ListExampleActivity;
import com.demo.rssapplication.application.RssApplication;
import com.demo.rssapplication.common.utilities.Utils;
import com.google.firebase.auth.FirebaseUser;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusFragment;
import rx.Observable;

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

    @BindView(R.id.btn_login)
    Button mLoginBtn;

    @BindView(R.id.btn_signup)
    Button mSignUpBtn;

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

        /**
        RxTextView.textChanges(mEmailField)
                .subscribe(new Action1<CharSequence>() {
                    @Override
                    public void call(CharSequence charSequence) {
                        presenter.validateEmail(mEmailField);
                        Log.d(TAG, "call: " + mEmailField.getText().toString());
                    }
                });
        */

        /**
        RxTextView.textChanges(mEmailField)
                .subscribe(charSequence -> {
                    presenter.validateEmail(mEmailField);
                    Log.d(TAG, "call: " + mEmailField.getText().toString());
                });

        RxTextView.textChanges(mPasswordField)
                .subscribe(charSequence -> {
                    presenter.validatePassword(mPasswordField);
                    Log.d(TAG, "call: " + mPasswordField.getText().toString());
                });
        */

        // Check Login and SignUp enable
        Observable<TextViewTextChangeEvent> emailChangeObservable = RxTextView.textChangeEvents(mEmailField);
        Observable<TextViewTextChangeEvent> passwordChangeObservable = RxTextView.textChangeEvents(mPasswordField);

        emailChangeObservable.subscribe(charSequence -> {
            presenter.validateEmail(mEmailField);
            Log.d(TAG, "call: " + mEmailField.getText().toString());
        });

        passwordChangeObservable.subscribe(charSequence -> {
            presenter.validatePassword(mPasswordField);
            Log.d(TAG, "call: " + mPasswordField.getText().toString());
        });

        // force-disable the button
        mLoginBtn.setEnabled(false);
        mSignUpBtn.setEnabled(false);

        Observable.combineLatest(emailChangeObservable, passwordChangeObservable, (emailObservable, passwordObservable) -> {
            boolean emailCheck = Utils.isValidEmail(emailObservable.text());
            boolean passwordCheck = passwordObservable.text().length() >= 8;
            return emailCheck && passwordCheck;
        }).subscribe(aBoolean -> {
            mLoginBtn.setEnabled(aBoolean);
            mSignUpBtn.setEnabled(aBoolean);

            if (aBoolean) {
                mLoginBtn.setBackgroundColor(ContextCompat.getColor(RssApplication.getContext(), R.color.colorPrimary));
                mSignUpBtn.setBackgroundColor(ContextCompat.getColor(RssApplication.getContext(), R.color.colorPrimary));
            } else {
                mLoginBtn.setBackgroundColor(ContextCompat.getColor(RssApplication.getContext(), R.color.colorAccent));
                mSignUpBtn.setBackgroundColor(ContextCompat.getColor(RssApplication.getContext(), R.color.colorAccent));
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
