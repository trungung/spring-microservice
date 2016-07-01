package com.demo.rssapplication.activity.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.demo.rssapplication.R;
import com.demo.rssapplication.activity.example.ListExampleActivity;
import com.demo.rssapplication.common.utilities.KeyboardUtils;
import com.demo.rssapplication.common.utilities.Utils;
import com.hkm.ui.processbutton.iml.ActionProcessButton;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.RxLifecycle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusFragment;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * A placeholder fragment containing a simple view.
 */
@RequiresPresenter(SignUpPresenterImpl.class)
public class SignUpFragment extends NucleusFragment<SignUpPresenterImpl> implements SignUpView, View.OnClickListener {

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.edt_email)
    MaterialEditText mEmailField;

    @BindView(R.id.edt_password)
    MaterialEditText mPasswordField;

    @BindView(R.id.btn_signin)
    ActionProcessButton mSigninBtn;

    @BindView(R.id.error_txt)
    TextView mErrorTxt;

    public Animation shake;

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
        presenter.onTakeView(this);

        // Init shake animation
        shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        mErrorTxt.setVisibility(View.GONE);

        // Check Login and SignUp enable
        Observable<TextViewTextChangeEvent> emailChangeObservable = RxTextView.textChangeEvents(mEmailField);
        Observable<TextViewTextChangeEvent> passwordChangeObservable = RxTextView.textChangeEvents(mPasswordField);

        emailChangeObservable.subscribe(charSequence -> {
            //presenter.validateEmail(mEmailField);
            Log.d(TAG, "call: " + mEmailField.getText().toString());
        });

        passwordChangeObservable.subscribe(charSequence -> {
            //presenter.validatePassword(mPasswordField);
            Log.d(TAG, "call: " + mPasswordField.getText().toString());
        });

        emailChangeObservable.compose(RxLifecycle.bindActivity(subject));
        passwordChangeObservable.compose(RxLifecycle.bindActivity(subject));

        // force-disable the button
        mSigninBtn.setMode(ActionProcessButton.Mode.ENDLESS);
        mSigninBtn.setProgress(0);

        Observable.combineLatest(emailChangeObservable, passwordChangeObservable, (emailObservable, passwordObservable) -> {
            boolean emailCheck = Utils.isValidEmail(emailObservable.text());
            boolean passwordCheck = passwordObservable.text().length() >= 8;
            return emailCheck && passwordCheck;
        }).compose(RxLifecycle.bindActivity(subject)).subscribe(aBoolean -> {
            mSigninBtn.setEnabled(aBoolean);
            if (aBoolean)
                mErrorTxt.setVisibility(View.GONE);
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

        RxView.clicks(view).subscribe(aVoid -> {
            // Hide keyboard
            if (!KeyboardUtils.hideKeyboard(mEmailField)) {
                KeyboardUtils.hideKeyboard(mPasswordField);
            }

            getPresenter().validateEmail(mEmailField);
            getPresenter().validatePassword(mPasswordField);
        });

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

    @OnClick(R.id.btn_signin_overlay)
    public void signInAction() {

        // Hide keyboard
        if (!KeyboardUtils.hideKeyboard(mEmailField)) {
            KeyboardUtils.hideKeyboard(mPasswordField);
        }

        // Start login
        mErrorTxt.setVisibility(View.GONE);
        getPresenter().login(mEmailField.getText().toString(), mPasswordField.getText().toString());
    }

    @Override
    public void showProgress() {
        mSigninBtn.setProgress(30);
//        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
//        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setEmailError() {
        mEmailField.clearAnimation();
        Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        mEmailField.startAnimation(shake);
    }

    @Override
    public void setPasswordError() {
        mPasswordField.clearAnimation();
        Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        mPasswordField.startAnimation(shake);
    }

    @Override
    public void navigateToHome() {
        mSigninBtn.setProgress(100);
        Intent intent = new Intent(getActivity(), ListExampleActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void showError() {
        mSigninBtn.setProgress(0);
        mEmailField.clearAnimation();
        Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        mEmailField.startAnimation(shake);
        mPasswordField.clearAnimation();
        Animation shake2 = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        mPasswordField.startAnimation(shake2);

        mErrorTxt.setVisibility(View.VISIBLE);
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
}
