package com.egrobots.prochat.presentation.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.egrobots.prochat.R;
import com.egrobots.prochat.di.ViewModelProviderFactory;
import com.egrobots.prochat.utils.Utils;
import com.egrobots.prochat.viewmodels.AuthenticationViewModel;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import dagger.android.support.DaggerAppCompatActivity;

public class LoginActivity extends DaggerAppCompatActivity {

    private static final int PASSWORD_MIN_LENGTH = 6;

    private AuthenticationViewModel authenticationViewModel;
    @Inject
    ViewModelProviderFactory providerFactory;
    @BindView(R.id.mobile_email_edit_text)
    EditText mobileEmailEditText;
    @BindView(R.id.password_edit_text)
    EditText passwordEditText;
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.forget_password_textview)
    TextView forgotPasswordTextView;
    @BindView(R.id.sign_up_textview)
    TextView signUpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        authenticationViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(AuthenticationViewModel.class);
        observeSignIn();
        observeError();
    }

    private void observeSignIn() {
        authenticationViewModel.observeAuthenticateState().observe(this, success -> {
            if (success) {
                Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
            } else {
                Toast.makeText(LoginActivity.this, "invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void observeError() {
        authenticationViewModel.observeErrorState().observe(this, error -> {
            Toast.makeText(LoginActivity.this, error, Toast.LENGTH_LONG).show();
        });
    }

    @OnClick(R.id.login_button)
    public void onLoginClicked() {
        String emailOrPhone = mobileEmailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (Utils.isEmailValid(emailOrPhone)) {
            //login with email
            authenticationViewModel.signInWithEmail(emailOrPhone, password);
        } else if (Utils.isPhoneValid(emailOrPhone)) {
            authenticationViewModel.signInWithPhone(emailOrPhone, password);
        } else {
            Toast.makeText(this, "Not valid email or phone", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.forget_password_textview)
    public void onForgotPasswordClicked() {
        startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
    }

    @OnClick(R.id.sign_up_textview)
    public void onSignUpClicked() {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
    }

    @OnTextChanged(R.id.mobile_email_edit_text)
    public void onEmailTextChanged(CharSequence s, int start, int count, int after) {
        if (s.length() == 0) {
            mobileEmailEditText.setError(null);
            return;
        }
        //validate email
        if (!Utils.isEmailValid(s.toString()) && !Utils.isPhoneValid(s.toString())) {
            mobileEmailEditText.setError("Not valid email or phone");
            loginButton.setBackground(getResources().getDrawable(R.drawable.dimmed_button_bg));
            return;
        }
        String password = passwordEditText.getText().toString();
        if (password.length() >= PASSWORD_MIN_LENGTH) {
            loginButton.setEnabled(true);
            loginButton.setBackground(getResources().getDrawable(R.drawable.active_button_bg));
        } else {
            loginButton.setEnabled(false);
            loginButton.setBackground(getResources().getDrawable(R.drawable.dimmed_button_bg));
        }
    }

    @OnTextChanged(R.id.password_edit_text)
    public void onPasswordTextChanged(CharSequence s, int start, int count, int after) {
        String emailPhone = mobileEmailEditText.getText().toString();
        if ((Utils.isEmailValid(emailPhone) || Utils.isPhoneValid(emailPhone)) && s.length() >= PASSWORD_MIN_LENGTH) {
            loginButton.setEnabled(true);
            loginButton.setBackground(getResources().getDrawable(R.drawable.active_button_bg));
        } else {
            loginButton.setEnabled(false);
            loginButton.setBackground(getResources().getDrawable(R.drawable.dimmed_button_bg));
        }
    }
}