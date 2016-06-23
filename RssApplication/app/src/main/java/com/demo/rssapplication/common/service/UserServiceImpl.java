package com.demo.rssapplication.common.service;

//public class UserServiceImpl implements UserService, FirebaseAuth.AuthStateListener {
//
//    private static final String TAG = UserService.class.getSimpleName();
//    private static UserService mInstance = null;
//
//    private Realm mRealm;
//    private FirebaseAuth mAuth;
//
//    /**
//     * The singleton to get the UserService instance
//     *
//     * @return {@link UserService}
//     */
//    public static UserService getInstance() {
//
//        if (mInstance == null) {
//            mInstance = new UserServiceImpl();
//        }
//
//        return mInstance;
//    }
//
//    private UserServiceImpl() {
//        mRealm = Realm.getDefaultInstance();
//
//        mAuth = FirebaseAuth.getInstance();
//        mAuth.addAuthStateListener(this);
//    }
//
//    @Override
//    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//        FirebaseUser user = firebaseAuth.getCurrentUser();
//        if (user != null) {
//            // User is signed in
//            Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//        } else {
//            // User is signed out
//            Log.d(TAG, "onAuthStateChanged:signed_out");
//        }
//        // updateUI(user);
//    }
//
//    @Override
//    public void signOut() {
//
//    }
//
//    @Override
//    public void signIn(String email, String password) {
//
//    }
//
//    @Override
//    public void createAccount(String email, String password) {
////        mAuth.createUserWithEmailAndPassword(email, password)
////                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
////                    @Override
////                    public void onComplete(@NonNull Task<AuthResult> task) {
////                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
////
////                        // If sign in fails, display a message to the user. If sign in succeeds
////                        // the auth state listener will be notified and logic to handle the
////                        // signed in user can be handled in the listener.
////                        if (!task.isSuccessful()) {
////
////                            Toast.makeText(getActivity(), "Authentication failed.",
////                                    Toast.LENGTH_SHORT).show();
////                        }
////
////                        hideProgressDialog();
////                    }
////                });
//    }
//}
