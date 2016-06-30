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
import com.hkm.ui.processbutton.iml.ActionProcessButton;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.RxLifecycle;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusFragment;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

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

    @BindView(R.id.btn_progress)
    ActionProcessButton mProgressBtn;

    PublishSubject<ActivityEvent> subject = PublishSubject.create();
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

        emailChangeObservable.compose(RxLifecycle.bindActivity(subject));
        passwordChangeObservable.compose(RxLifecycle.bindActivity(subject));

        // force-disable the button
        mLoginBtn.setEnabled(false);
        mSignUpBtn.setEnabled(true);
        mProgressBtn.setMode(ActionProcessButton.Mode.ENDLESS);
        mProgressBtn.setProgress(0);

        Observable.combineLatest(emailChangeObservable, passwordChangeObservable, (emailObservable, passwordObservable) -> {
            boolean emailCheck = Utils.isValidEmail(emailObservable.text());
            boolean passwordCheck = passwordObservable.text().length() >= 8;
            return emailCheck && passwordCheck;
        }).compose(RxLifecycle.bindActivity(subject)).subscribe(aBoolean -> {
            mLoginBtn.setEnabled(aBoolean);
            mSignUpBtn.setEnabled(aBoolean);

            if (aBoolean) {
                mLoginBtn.setBackgroundColor(ContextCompat.getColor(RssApplication.getContext(), R.color.colorPrimary));
                mSignUpBtn.setBackgroundColor(ContextCompat.getColor(RssApplication.getContext(), R.color.colorPrimary));
                mProgressBtn.setProgress(100);
            } else {
                mLoginBtn.setBackgroundColor(ContextCompat.getColor(RssApplication.getContext(), R.color.colorAccent));
                mSignUpBtn.setBackgroundColor(ContextCompat.getColor(RssApplication.getContext(), R.color.colorAccent));
                mProgressBtn.setProgress(30);
                mProgressBtn.setText("Validating...");
            }
        });

        /**
        RxTextView.textChangeEvents(mEmailField)
                .filter(new Func1<TextViewTextChangeEvent, Boolean>() {
                    @Override
                    public Boolean call(TextViewTextChangeEvent e) {
                        return e.text().length() >= 3;
                    }
                })
                .subscribe(new Action1<TextViewTextChangeEvent>() {
                    @Override
                    public void call(TextViewTextChangeEvent e) {
                        Log.d(TAG, "call: " + e.text());
                    }
                });


        RxTextView.textChangeEvents(mEmailField)
                .filter(e -> e.text().length() >= 3)
                .subscribe(e -> {
                    Log.d(TAG, "call: " + e.text());
                });

         RxView.clicks(mLoginBtn).subscribe(aVoid -> {
            loginAction();
         });

         RxView.clicks(mSignUpBtn).subscribe(aVoid -> {
            signUpAction();
         });

        */

        return view;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

        subject.onNext(ActivityEvent.DESTROY);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

        subject.onNext(ActivityEvent.STOP);
    }

    @OnClick(R.id.btn_signup)
    public void signUpAction() {

        mProgressBtn.setProgress(30);
        mProgressBtn.setText("Signing Up...");
        Observable.interval(3, TimeUnit.SECONDS)
                .doOnUnsubscribe(() -> Log.i(TAG, "Unsubscribing subscription from onCreate()"))
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycle.bindActivity(subject))
                .takeUntil(subject)
                .subscribe(num -> {
                    Log.i(TAG, "Started in onCreate(), running until onPause(): " + num);
//                    mProgressBtn.setProgress(100);
                    Intent intent = new Intent(getActivity(), ListExampleActivity.class);
                    startActivity(intent);
                });

//        getPresenter().signUp(mEmailField.getText().toString(), mPasswordField.getText().toString());
    }

    @OnClick(R.id.btn_login)
    public void loginAction() {

        mProgressBtn.setProgress(30);
        mProgressBtn.setText("Loging...");
        Observable.interval(3, TimeUnit.SECONDS)
                .doOnUnsubscribe(() -> Log.i(TAG, "Unsubscribing subscription from onCreate()"))
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycle.bindActivity(subject))
                .takeUntil(subject)
                .subscribe(num -> {
                    Log.i(TAG, "Started in onCreate(), running until onPause(): " + num);
//                    mProgressBtn.setProgress(100);
                    Intent intent = new Intent(getActivity(), ListExampleActivity.class);
                    startActivity(intent);
                });

//        getPresenter().login(mEmailField.getText().toString(), mPasswordField.getText().toString());
    }

    @Override
    public void showProgress() {
        mProgressBtn.setProgress(30);
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
        hideProgress();
        if (user != null) {
        } else {
        }
    }
}
