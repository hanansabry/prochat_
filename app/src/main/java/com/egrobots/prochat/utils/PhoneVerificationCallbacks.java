package com.egrobots.prochat.utils;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import androidx.annotation.NonNull;

public class PhoneVerificationCallbacks extends PhoneAuthProvider.OnVerificationStateChangedCallbacks {

    private String verificationId;
    private String smsCode;
    private String error;

    @Override
    public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
        super.onCodeAutoRetrievalTimeOut(s);
    }

    @Override
    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
        super.onCodeSent(s, forceResendingToken);
        verificationId = s;
    }

    @Override
    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
        smsCode = phoneAuthCredential.getSmsCode();
    }

    @Override
    public void onVerificationFailed(@NonNull FirebaseException e) {
        error = e.getMessage();
    }

    public String getVerificationId() {
        return verificationId;
    }

    public void setVerificationId(String verificationId) {
        this.verificationId = verificationId;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
