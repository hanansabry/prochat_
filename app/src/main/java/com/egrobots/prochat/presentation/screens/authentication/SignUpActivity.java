package com.egrobots.prochat.presentation.screens.authentication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.egrobots.prochat.R;
import com.egrobots.prochat.di.ViewModelProviderFactory;
import com.egrobots.prochat.presentation.screens.authentication.authenticate_api.SignUpAuthentication;
import com.egrobots.prochat.utils.Constants;
import com.egrobots.prochat.presentation.viewmodels.AuthenticationViewModel;

import javax.inject.Inject;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import dagger.android.support.DaggerAppCompatActivity;

public class SignUpActivity extends DaggerAppCompatActivity {

    private static final int PASSWORD_MIN_LENGTH = 6;

    private boolean signUpWithEmail;
    @Inject
    ViewModelProviderFactory providerFactory;
    @BindView(R.id.main_layout)
    View mainLayout;
    @BindView(R.id.mobile_email_edit_text)
    EditText mobileEmailEditText;
    @BindView(R.id.username_edit_text)
    EditText usernameEditText;
    @BindView(R.id.password_edit_text)
    EditText passwordEditText;
    @BindView(R.id.signup_mobile_button)
    Button mobileSignUpButton;
    @BindView(R.id.signup_email_button)
    Button emailSignUpButton;
    @BindView(R.id.signup_button)
    Button signUpButton;
    @BindView(R.id.accept_terms_checkbox)
    CheckBox acceptTermsCheckbox;
    @BindView(R.id.mobile_country_code_textview)
    TextView mobileCountryCodeTextView;
    private SignUpAuthentication signUpAuthentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        AuthenticationViewModel authenticationViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(AuthenticationViewModel.class);
        signUpAuthentication = new ActivitySignUpAuthentication();
        signUpAuthentication.initializeViews(this,
                authenticationViewModel,
                mainLayout,
                mobileEmailEditText,
                usernameEditText,
                passwordEditText,
                mobileSignUpButton,
                emailSignUpButton,
                signUpButton,
                acceptTermsCheckbox,
                mobileCountryCodeTextView);
        signUpAuthentication.initializeObservers(this);
    }


    @OnClick(R.id.signup_button)
    public void onSignUpClicked() {
        signUpAuthentication.onSignUpClicked();
    }

    @OnClick(R.id.sign_in_textview)
    public void onSignInClicked() {
        signUpAuthentication.onSignInClicked();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.signup_email_button)
    public void onSignUpWithEmailClicked() {
        signUpAuthentication.onSignUpWithEmailClicked();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.signup_mobile_button)
    public void onSignUpWithMobileClicked() {
        signUpAuthentication.onSignUpWithMobileClicked();
    }

    @OnTextChanged(R.id.mobile_email_edit_text)
    public void onMobileEmailTextChanged(CharSequence s, int start, int count, int after) {
        signUpAuthentication.onMobileEmailTextChanged(s, start, count, after);
    }

    @OnTextChanged(R.id.username_edit_text)
    public void onUserNameTextChanged(CharSequence s, int start, int count, int after) {
        signUpAuthentication.onUserNameTextChanged(s, start, count, after);
    }

    @OnTextChanged(R.id.password_edit_text)
    public void onPasswordTextChanged(CharSequence s, int start, int count, int after) {
        signUpAuthentication.onPasswordTextChanged(s, start, count, after);
    }

    private class ActivitySignUpAuthentication extends SignUpAuthentication {

        @Override
        protected void goToVerificationScreen(String phoneNumber, String userName, String password) {
            Intent intent = new Intent(SignUpActivity.this, VerifyAccount.class);
            intent.putExtra(Constants.PHONE, phoneNumber);
            intent.putExtra(Constants.USER_NAME, userName);
            intent.putExtra(Constants.PASSWORD, password);
            startActivity(intent);
        }

        @Override
        protected void goToLoginScreen() {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        }

        @Override
        protected void signUpWithEmailSuccessAction() {
            //show the user that email verification is sent
            Toast.makeText(SignUpActivity.this, "Email verification has been sent to your mail, Please check it.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
        }

        @Override
        protected void signUpWithEmailErrorAction(String error) {
            Toast.makeText(SignUpActivity.this, error, Toast.LENGTH_SHORT).show();
        }
    }
}