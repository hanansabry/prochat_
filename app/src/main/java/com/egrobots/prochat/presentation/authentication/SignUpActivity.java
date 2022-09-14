package com.egrobots.prochat.presentation.authentication;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.egrobots.prochat.R;
import com.egrobots.prochat.di.ViewModelProviderFactory;
import com.egrobots.prochat.utils.Constants;
import com.egrobots.prochat.utils.Utils;
import com.egrobots.prochat.viewmodels.AuthenticationViewModel;
import com.google.android.material.snackbar.Snackbar;

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
    private AuthenticationViewModel authenticationViewModel;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        authenticationViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(AuthenticationViewModel.class);
        observeSigningUpWithEmail();
        observeError();
    }

    private void observeSigningUpWithEmail() {
        authenticationViewModel.observeAuthenticateState().observe(this, success -> {
            if (success) {
                //show the user that email verification is sent
                Snackbar snackbar = Snackbar.make(mainLayout, "Email verification has been sent to your mail, Please check it.", Snackbar.LENGTH_INDEFINITE);
                snackbar.show();
                startActivity(new Intent(this, MainActivity.class));
            } else {
                Toast.makeText(SignUpActivity.this, "Can't verify your email", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void observeError() {
        authenticationViewModel.observeErrorState().observe(this, error -> {
            Toast.makeText(SignUpActivity.this, error, Toast.LENGTH_LONG).show();
        });
    }

    @OnClick(R.id.signup_button)
    public void onSignUpClicked() {
        String mobileOrEmail = mobileEmailEditText.getText().toString();
        String userName = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (signUpWithEmail) {
            authenticationViewModel.signUpWithEmail(mobileOrEmail, userName, password);
        } else {
            String phoneNumber = "+20" + (mobileOrEmail.startsWith("0") ? mobileOrEmail.replaceFirst("0", "") : mobileOrEmail);
            Intent intent = new Intent(this, VerifyAccount.class);
            intent.putExtra(Constants.PHONE, phoneNumber);
            intent.putExtra(Constants.USER_NAME, userName);
            intent.putExtra(Constants.PASSWORD, password);
            startActivity(intent);
        }
    }

    @OnClick(R.id.sign_in_textview)
    public void onSignInClicked() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.signup_email_button)
    public void onSignUpWithEmailClicked() {
        signUpWithEmail = true;
        //set email selected
        emailSignUpButton.setBackground(getResources().getDrawable(R.drawable.button_tab_selected));
        emailSignUpButton.setTextColor(getResources().getColor(R.color.White));
        emailSignUpButton.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.White)));

        //set mobile unselected
        mobileSignUpButton.setBackground(null);
        mobileSignUpButton.setTextColor(getResources().getColor(R.color.DarkGray));
        mobileSignUpButton.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.DarkGray)));

        mobileEmailEditText.setHint(R.string.email);
        mobileEmailEditText.setText("");
        mobileEmailEditText.setError(null);
        mobileEmailEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        mobileCountryCodeTextView.setVisibility(View.GONE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.signup_mobile_button)
    public void onSignUpWithMobileClicked() {
        signUpWithEmail = false;
        //set mobile selected
        mobileSignUpButton.setBackground(getResources().getDrawable(R.drawable.button_tab_selected));
        mobileSignUpButton.setTextColor(getResources().getColor(R.color.White));
        mobileSignUpButton.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.White)));

        //set email unselected
        emailSignUpButton.setBackground(null);
        emailSignUpButton.setTextColor(getResources().getColor(R.color.DarkGray));
        emailSignUpButton.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.DarkGray)));

        mobileEmailEditText.setHint("Mobile");
        mobileEmailEditText.setText("");
        mobileEmailEditText.setError(null);
        mobileEmailEditText.setInputType(InputType.TYPE_CLASS_PHONE);
        mobileCountryCodeTextView.setVisibility(View.VISIBLE);
    }

    @OnTextChanged(R.id.mobile_email_edit_text)
    public void onMobileEmailTextChanged(CharSequence s, int start, int count, int after) {
        String userName = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (signUpWithEmail) {
            if (!Utils.isEmailValid(s.toString())) {
                mobileEmailEditText.setError("The email is not valid");
                signUpButton.setBackground(getResources().getDrawable(R.drawable.dimmed_button_bg));
                return;
            }
        } else {
            if (!Utils.isPhoneValid(s.toString())) {
                mobileEmailEditText.setError("The phone number is not valid");
                signUpButton.setBackground(getResources().getDrawable(R.drawable.dimmed_button_bg));
                return;
            }
        }

        if (userName.length() > 0 && password.length() > PASSWORD_MIN_LENGTH) {
            signUpButton.setBackground(getResources().getDrawable(R.drawable.active_button_bg));
            signUpButton.setEnabled(true);
        } else {
            signUpButton.setBackground(getResources().getDrawable(R.drawable.dimmed_button_bg));
            signUpButton.setEnabled(false);
        }

    }

    @OnTextChanged(R.id.username_edit_text)
    public void onUserNameTextChanged(CharSequence s, int start, int count, int after) {
        String mobileEmail = mobileEmailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        boolean mobileEmailValid = signUpWithEmail ? Utils.isEmailValid(mobileEmail) : Utils.isPhoneValid(mobileEmail);
        if (mobileEmailValid && s.length() > 0 && password.length() >= PASSWORD_MIN_LENGTH) {
            signUpButton.setBackground(getResources().getDrawable(R.drawable.active_button_bg));
            signUpButton.setEnabled(true);
        } else {
            signUpButton.setBackground(getResources().getDrawable(R.drawable.dimmed_button_bg));
            signUpButton.setEnabled(false);
        }
    }

    @OnTextChanged(R.id.password_edit_text)
    public void onPasswordTextChanged(CharSequence s, int start, int count, int after) {
        String mobileEmail = mobileEmailEditText.getText().toString();
        String userName = usernameEditText.getText().toString();

        boolean mobileEmailValid = signUpWithEmail ? Utils.isEmailValid(mobileEmail) : Utils.isPhoneValid(mobileEmail);
        if (mobileEmailValid && userName.length() > 0 && s.length() >= PASSWORD_MIN_LENGTH) {
            signUpButton.setBackground(getResources().getDrawable(R.drawable.active_button_bg));
            signUpButton.setEnabled(true);
        } else {
            signUpButton.setBackground(getResources().getDrawable(R.drawable.dimmed_button_bg));
            signUpButton.setEnabled(false);
        }
    }
}