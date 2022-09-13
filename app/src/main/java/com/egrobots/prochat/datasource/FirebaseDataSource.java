package com.egrobots.prochat.datasource;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.Single;

public class FirebaseDataSource {

    private FirebaseAuth firebaseAuth;

    @Inject
    public FirebaseDataSource(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public Single<Boolean> signIn(String emailOrPhone, String password) {
        return Single.create(emitter -> {
            firebaseAuth.signInWithEmailAndPassword(emailOrPhone, password)
                    .addOnSuccessListener(authResult -> emitter.onSuccess(true))
                    .addOnFailureListener(emitter::onError);
        });
    }

    public Single<Boolean> signUp(String emailOrPhone, String userName, String password, boolean signUpWithMail) {
        return Single.create(emitter -> {
            if (signUpWithMail) {
                firebaseAuth.createUserWithEmailAndPassword(emailOrPhone, password)
                        .addOnSuccessListener(authResult -> {
                            //send verification mail to the user
                            FirebaseUser firebaseUser = authResult.getUser();
                            firebaseUser.sendEmailVerification().addOnSuccessListener(aVoid -> emitter.onSuccess(true))
                            .addOnFailureListener(e -> emitter.onSuccess(false));
                        })
                        .addOnFailureListener(emitter::onError);
            } else {
                //create account with phone number
            }
        });
    }
}
