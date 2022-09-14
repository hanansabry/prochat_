package com.egrobots.prochat.presentation.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.egrobots.prochat.R;
import com.egrobots.prochat.di.ViewModelProviderFactory;
import com.egrobots.prochat.utils.Constants;
import com.egrobots.prochat.viewmodels.AuthenticationViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import dagger.android.support.DaggerAppCompatActivity;

public class VerifyAccount extends DaggerAppCompatActivity {

    private AuthenticationViewModel authenticationViewModel;
    @Inject
    ViewModelProviderFactory providerFactory;
    @BindView(R.id.code_1_edit_text)
    EditText code1EditText;
    @BindView(R.id.code_2_edit_text)
    EditText code2EditText;
    @BindView(R.id.code_3_edit_text)
    EditText code3EditText;
    @BindView(R.id.code_4_edit_text)
    EditText code4EditText;
    @BindView(R.id.code_5_edit_text)
    EditText code5EditText;
    @BindView(R.id.code_6_edit_text)
    EditText code6EditText;
    @BindView(R.id.verify_button)
    Button verifyButton;
    private String verificationId;
    private String phoneNumber;
    private String username;
    private String password;
    private String smsCode = "123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_account);
        ButterKnife.bind(this);
        phoneNumber = getIntent().getStringExtra(Constants.PHONE);
        username = getIntent().getStringExtra(Constants.USER_NAME);
        password = getIntent().getStringExtra(Constants.PASSWORD);

        authenticationViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(AuthenticationViewModel.class);
        PhoneAuthOptions phoneAuthOptions = getPhoneAuthOptions(phoneNumber);
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);
        observeSigningUpWithPhone();
        observeError();
    }

    private void observeSigningUpWithPhone() {
        authenticationViewModel.observeAuthenticateState().observe(this, success -> {
            //show the user that email verification is sent
            Toast.makeText(VerifyAccount.this, "Sign up successfully", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class));
        });
    }

    private void observeError() {
        authenticationViewModel.observeErrorState().observe(this, error -> {
            Toast.makeText(VerifyAccount.this, error, Toast.LENGTH_LONG).show();
        });
    }

    private PhoneAuthOptions getPhoneAuthOptions(String phone) {
        return PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                .setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
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
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(VerifyAccount.this, "error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).build();
    }

    @OnClick(R.id.verify_button)
    public void onVerifyClicked() {
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
                            Toast.makeText(VerifyAccount.this, "Sign up successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(VerifyAccount.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @OnTextChanged(R.id.code_1_edit_text)
    public void onCode1TextChanged(CharSequence s, int start, int count, int after) {
        setVerifyButtonEnabled();
        code2EditText.requestFocus();
    }

    @OnTextChanged(R.id.code_2_edit_text)
    public void onCode2TextChanged(CharSequence s, int start, int count, int after) {
        setVerifyButtonEnabled();
        code3EditText.requestFocus();
    }

    @OnTextChanged(R.id.code_3_edit_text)
    public void onCode3TextChanged(CharSequence s, int start, int count, int after) {
        setVerifyButtonEnabled();
        code4EditText.requestFocus();
    }

    @OnTextChanged(R.id.code_4_edit_text)
    public void onCode4TextChanged(CharSequence s, int start, int count, int after) {
        setVerifyButtonEnabled();
        code5EditText.requestFocus();
    }

    @OnTextChanged(R.id.code_5_edit_text)
    public void onCode5TextChanged(CharSequence s, int start, int count, int after) {
        setVerifyButtonEnabled();
        code6EditText.requestFocus();
    }

    @OnTextChanged(R.id.code_6_edit_text)
    public void onCode6TextChanged(CharSequence s, int start, int count, int after) {
        setVerifyButtonEnabled();
    }

    private void setVerifyButtonEnabled() {
        if (isCodeEntered()) {
            verifyButton.setEnabled(true);
            verifyButton.setBackground(getResources().getDrawable(R.drawable.active_button_bg));
        } else {
            verifyButton.setEnabled(false);
            verifyButton.setBackground(getResources().getDrawable(R.drawable.dimmed_button_bg));
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