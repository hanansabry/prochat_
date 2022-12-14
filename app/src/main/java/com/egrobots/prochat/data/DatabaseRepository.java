package com.egrobots.prochat.data;

import com.egrobots.prochat.datasource.FirebaseDataSource;
import com.egrobots.prochat.model.Chat;
import com.egrobots.prochat.model.Group;
import com.google.firebase.auth.PhoneAuthCredential;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class DatabaseRepository {

    private FirebaseDataSource firebaseDataSource;

    @Inject
    public DatabaseRepository(FirebaseDataSource firebaseDataSource) {
        this.firebaseDataSource = firebaseDataSource;
    }

    public Single<Boolean> signIn(String emailOrPhone, String password) {
        return firebaseDataSource.signInWithEmail(emailOrPhone, password);
    }

    public Single<Boolean> signInWithPhone(String phone, String password) {
        return firebaseDataSource.signInWithPhone(phone, password);
    }

    public Single<Boolean> signUpWithEmail(String emailOrPhone, String userName, String password) {
        return firebaseDataSource.signUpWithEmail(emailOrPhone, userName, password);
    }

    public Single<Boolean> signUpWithPhone(PhoneAuthCredential credential, String phone, String phoneVerificationId, String username, String password) {
        return firebaseDataSource.signUpWithPhone(credential, phone, phoneVerificationId, username, password);
    }

    public Single<Boolean> sendPasswordResetEmail(String email) {
        return firebaseDataSource.sendPasswordResetEmail(email);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //user methods
    public Single<Boolean> isUserHasGroups(String userId) {
        return firebaseDataSource.isUserHasGroups(userId);
    }

    public Flowable<Group> getUserGroups(String userId) {
        return firebaseDataSource.getUserGroups(userId);
    }

    public Flowable<Chat> getGroupChats(String groupId) {
        return firebaseDataSource.getGroupChats(groupId);
    }
}