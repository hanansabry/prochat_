package com.egrobots.prochat.presentation.screens.authentication.authenticate_api;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.egrobots.prochat.R;
import com.egrobots.prochat.presentation.screens.authentication.VerifyAccount;
import com.egrobots.prochat.presentation.viewmodels.AuthenticationViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

public abstract class VerificationAuthentication {

    private static final int PASSWORD_MIN_LENGTH = 6;
    private AuthenticationViewModel authenticationViewModel;
    private Context activity;
    EditText code1EditText;
    EditText code2EditText;
    EditText code3EditText;
    EditText code4EditText;
    EditText code5EditText;
    EditText code6EditText;
    Button verifyButton;
    private String verificationId;
    private String smsCode = "123456";
    int seconds = 60;
    Runnable verificationRunnable;
    private Handler verificationMailCounterHandler;

    public void initializeViews(Context activity,
                                AuthenticationViewModel authenticationViewModel,
                                TextView verifyAccountDesc,
                                TextView receiveMailSecondsCount,
                                EditText code1EditText,
                                EditText code2EditText,
                                EditText code3EditText,
                                EditText code4EditText,
                                EditText code5EditText,
                                EditText code6EditText,
                                Button verifyButton,
                                String phoneNumber) {
        this.activity = activity;
        this.authenticationViewModel = authenticationViewModel;
        this.code1EditText = code1EditText;
        this.code2EditText = code2EditText;
        this.code3EditText = code3EditText;
        this.code4EditText = code4EditText;
        this.code5EditText = code5EditText;
        this.code6EditText = code6EditText;
        this.verifyButton = verifyButton;

        verifyAccountDesc.setText(String.format(activity.getString(R.string.verify_account_desc), phoneNumber));
        //set receive code counter
        setReceiveVerificationCodeCounter(receiveMailSecondsCount);
    }

    private void setReceiveVerificationCodeCounter(TextView receiveMailSecondsCount) {
        verificationMailCounterHandler = new Handler();
        verificationRunnable = () -> {
            receiveMailSecondsCount.setText(String.format(activity.getString(R.string.verification_code_message), (seconds--)+""));
            verificationMailCounterHandler.postDelayed(verificationRunnable, 1000);
            if (seconds == -1) {
                verificationMailCounterHandler.removeCallbacks(verificationRunnable);
            }
        };
        verificationMailCounterHandler.postDelayed(verificationRunnable, 1000);
    }

    public void initializeObservers(LifecycleOwner lifecycleOwner) {
        observeSigningUpWithPhone(lifecycleOwner);
        observeError(lifecycleOwner);
    }

    private void observeSigningUpWithPhone(LifecycleOwner lifecycleOwner) {
        authenticationViewModel.observeAuthenticateState().observe(lifecycleOwner, success -> {
            verificationSuccessAction();
        });
    }

    protected abstract void verificationSuccessAction();

    private void observeError(LifecycleOwner lifecycleOwner) {
        authenticationViewModel.observeErrorState().observe(lifecycleOwner, this::verificationErrorAction);
    }

    protected abstract void verificationErrorAction(String error);

    public void verifyPhoneNumber(String phone) {
        PhoneAuthOptions phoneAuthOptions = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                .setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity((Activity) activity)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        verificationId = s;
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        smsCode = phoneAuthCredential.getSmsCode();
                        code1EditText.setText(String.valueOf(smsCode.charAt(0)));
                        code2EditText.setText(String.valueOf(smsCode.charAt(1)));
                        code3EditText.setText(String.valueOf(smsCode.charAt(2)));
                        code4EditText.setText(String.valueOf(smsCode.charAt(3)));
                        code5EditText.setText(String.valueOf(smsCode.charAt(4)));
                        code6EditText.setText(String.valueOf(smsCode.charAt(5)));
                        verificationMailCounterHandler.removeCallbacks(verificationRunnable);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        verificationErrorAction("error: " + e.getMessage());
                        verificationMailCounterHandler.removeCallbacks(verificationRunnable);
                        Toast.makeText(activity, "Code must be resent", Toast.LENGTH_SHORT).show();
                    }
                }).build();
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);
    }

    public void onVerifyClicked(String phoneNumber, String username, String password) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, smsCode);
        authenticationViewModel.signUpWithPhone(credential, phoneNumber, verificationId, username, password);
//        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(activity, "Sign up successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(activity, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void onCode1TextChanged(CharSequence s, int start, int count, int after) {
        setVerifyButtonEnabled();
        code2EditText.requestFocus();
    }

    public void onCode2TextChanged(CharSequence s, int start, int count, int after) {
        setVerifyButtonEnabled();
        code3EditText.requestFocus();
    }

    public void onCode3TextChanged(CharSequence s, int start, int count, int after) {
        setVerifyButtonEnabled();
        code4EditText.requestFocus();
    }

    public void onCode4TextChanged(CharSequence s, int start, int count, int after) {
        setVerifyButtonEnabled();
        code5EditText.requestFocus();
    }

    public void onCode5TextChanged(CharSequence s, int start, int count, int after) {
        setVerifyButtonEnabled();
        code6EditText.requestFocus();
    }

    public void onCode6TextChanged(CharSequence s, int start, int count, int after) {
        setVerifyButtonEnabled();
    }

    private void setVerifyButtonEnabled() {
        if (isCodeEntered()) {
            verifyButton.setEnabled(true);
            verifyButton.setBackground(activity.getDrawable(R.drawable.active_button_bg));
        } else {
            verifyButton.setEnabled(false);
            verifyButton.setBackground(activity.getDrawable(R.drawable.dimmed_button_bg));
        }
    }

    private boolean isCodeEntered() {
        return !code1EditText.getText().toString().isEmpty() &&
                !code2EditText.getText().toString().isEmpty() &&
                !code3EditText.getText().toString().isEmpty() &&
                !code4EditText.getText().toString().isEmpty() &&
                !code5EditText.getText().toString().isEmpty() &&
                !code6EditText.getText().toString().isEmpty() &&
                verificationId != null;
    }

}
