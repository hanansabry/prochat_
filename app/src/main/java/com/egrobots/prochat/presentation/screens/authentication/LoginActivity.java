package com.egrobots.prochat.presentation.screens.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.egrobots.prochat.R;
import com.egrobots.prochat.di.ViewModelProviderFactory;
import com.egrobots.prochat.presentation.screens.authentication.authenticate_api.LoginAuthentication;
import com.egrobots.prochat.presentation.viewmodels.AuthenticationViewModel;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import dagger.android.support.DaggerAppCompatActivity;

public class LoginActivity extends DaggerAppCompatActivity {

    private static final int PASSWORD_MIN_LENGTH = 6;

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
    @BindView(R.id.search_user_edit_text)
    EditText searchUserEditText;
    private ActivityLoginAuthentication loginAuthentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        AuthenticationViewModel authenticationViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(AuthenticationViewModel.class);
        loginAuthentication = new ActivityLoginAuthentication();
        loginAuthentication.initializeViews(this,
                authenticationViewModel,
                mobileEmailEditText,
                passwordEditText,
                loginButton);
        loginAuthentication.initializeObservers(this);
    }

    @OnFocusChange(R.id.search_user_edit_text)
    public void onSearchEditTextFocusChange(View v, boolean hasFocus) {
        loginAuthentication.onSearchEditTextFocusChange(v, hasFocus);
    }

    @OnClick(R.id.login_button)
    public void onLoginClicked() {
        loginAuthentication.onLoginClicked();
    }

    @OnClick(R.id.forget_password_textview)
    public void onForgotPasswordClicked() {
        loginAuthentication.onForgotPasswordClicked();
    }

    @OnClick(R.id.sign_up_textview)
    public void onSignUpClicked() {
        loginAuthentication.onSignUpClicked();
    }

    @OnTextChanged(R.id.mobile_email_edit_text)
    public void onEmailTextChanged(CharSequence s, int start, int count, int after) {
        loginAuthentication.onEmailTextChanged(s, start, count, after);
    }

    @OnTextChanged(R.id.password_edit_text)
    public void onPasswordTextChanged(CharSequence s, int start, int count, int after) {
        loginAuthentication.onPasswordTextChanged(s, start, count, after);
    }

    private class ActivityLoginAuthentication extends LoginAuthentication {

        @Override
        protected void loginSuccessAction() {
            Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

        @Override
        protected void loginErrorAction(String error) {
            Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void goToForgetPasswordScreen() {
            startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
        }

        @Override
        protected void goToSignUpScreen() {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        }
    }
}