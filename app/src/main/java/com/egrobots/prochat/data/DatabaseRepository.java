package com.egrobots.prochat.data;

import com.egrobots.prochat.datasource.FirebaseDataSource;

import javax.inject.Inject;

import io.reactivex.Single;

public class DatabaseRepository {

    private FirebaseDataSource firebaseDataSource;

    @Inject
    public DatabaseRepository(FirebaseDataSource firebaseDataSource) {
        this.firebaseDataSource = firebaseDataSource;
    }

    public Single<Boolean> signIn(String emailOrPhone, String password) {
        return firebaseDataSource.signIn(emailOrPhone, password);
    }

    public Single<Boolean> signUp(String emailOrPhone, String userName, String password, boolean signUpWithMail) {
        return firebaseDataSource.signUp(emailOrPhone, userName, password, signUpWithMail);
    }
}
