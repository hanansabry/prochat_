package com.egrobots.prochat.presentation.screens.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.egrobots.prochat.R;
import com.egrobots.prochat.di.ViewModelProviderFactory;
import com.egrobots.prochat.presentation.screens.authentication.authenticate_api.VerificationAuthentication;
import com.egrobots.prochat.utils.Constants;
import com.egrobots.prochat.presentation.viewmodels.AuthenticationViewModel;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import dagger.android.support.DaggerAppCompatActivity;

public class VerifyAccount extends DaggerAppCompatActivity {

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
    private ActivityVerificationAuthentication verificationAuthentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_account);
        ButterKnife.bind(this);
        phoneNumber = getIntent().getStringExtra(Constants.PHONE);
        username = getIntent().getStringExtra(Constants.USER_NAME);
        password = getIntent().getStringExtra(Constants.PASSWORD);

        AuthenticationViewModel authenticationViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(AuthenticationViewModel.class);
        verificationAuthentication = new ActivityVerificationAuthentication();
        verificationAuthentication.initializeViews(this,
                authenticationViewModel,
                code1EditText,
                code2EditText,
                code3EditText,
                code4EditText,
                code6EditText,
                code6EditText,
                verifyButton);
        verificationAuthentication.verifyPhoneNumber(phoneNumber);
        verificationAuthentication.initializeObservers(this);
    }

    @OnClick(R.id.verify_button)
    public void onVerifyClicked() {
        verificationAuthentication.onVerifyClicked(phoneNumber, username, password);
    }

    @OnTextChanged(R.id.code_1_edit_text)
    public void onCode1TextChanged(CharSequence s, int start, int count, int after) {
        verificationAuthentication.onCode1TextChanged(s, start, count, after);
    }

    @OnTextChanged(R.id.code_2_edit_text)
    public void onCode2TextChanged(CharSequence s, int start, int count, int after) {
        verificationAuthentication.onCode2TextChanged(s, start, count, after);
    }

    @OnTextChanged(R.id.code_3_edit_text)
    public void onCode3TextChanged(CharSequence s, int start, int count, int after) {
        verificationAuthentication.onCode3TextChanged(s, start, count, after);
    }

    @OnTextChanged(R.id.code_4_edit_text)
    public void onCode4TextChanged(CharSequence s, int start, int count, int after) {
        verificationAuthentication.onCode4TextChanged(s, start, count, after);
    }

    @OnTextChanged(R.id.code_5_edit_text)
    public void onCode5TextChanged(CharSequence s, int start, int count, int after) {
        verificationAuthentication.onCode5TextChanged(s, start, count, after);
    }

    @OnTextChanged(R.id.code_6_edit_text)
    public void onCode6TextChanged(CharSequence s, int start, int count, int after) {
        verificationAuthentication.onCode6TextChanged(s, start, count, after);
    }

    private class ActivityVerificationAuthentication extends VerificationAuthentication {

        @Override
        protected void verificationSuccessAction() {
            Toast.makeText(VerifyAccount.this, "Sign up successfully", Toast.LENGTH_LONG).show();
            startActivity(new Intent(VerifyAccount.this, MainActivity.class));
        }

        @Override
        protected void verificationErrorAction(String error) {
            Toast.makeText(VerifyAccount.this, error, Toast.LENGTH_LONG).show();
        }
    }
}