package com.demo.rssapplication.common.service.firebase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class FirebaseServiceImpl implements FirebaseService, FirebaseAuth.AuthStateListener {

    private static final String TAG = FirebaseServiceImpl.class.getSimpleName();

    private static FirebaseService mInstance = null;
    private FirebaseAuth mAuth;
    private OnFirebaseAuthListener mListener;


    private FirebaseServiceImpl() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(this);
    }

    /**
     * The singleton to get the FirebaseService instance
     *
     * @return {@link FirebaseService}
     */
    public static FirebaseService getInstance() {

        if (mInstance == null) {
            mInstance = new FirebaseServiceImpl();
        }

        return mInstance;
    }

    public void destroy() {
        mAuth.removeAuthStateListener(this);
        mInstance = null;
    }

    @Override
    public void createUser(String email, String password, final OnFirebaseAuthListener listener) {

        this.mListener = listener;
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            listener.onFailure("Authentication failed.");
                        }
                    }
                });
    }

    @Override
    public void auth(String email, String password, final OnFirebaseAuthListener listener) {
        this.mListener = listener;
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            listener.onFailure("Authentication failed.");
                        }
                    }
                });
    }

    @Override
    public void signOut(OnFirebaseAuthListener listener) {
        this.mListener = listener;
        mAuth.signOut();
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            // User is signed in
            Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            this.mListener.onSuccess(user);
        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out");
            this.mListener.onFailure("onAuthStateChanged:signed_out");
        }
    }
}
