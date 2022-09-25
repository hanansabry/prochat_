package com.egrobots.prochat.presentation.screens.authentication.authenticate_api;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.egrobots.prochat.R;
import com.egrobots.prochat.utils.Utils;
import com.egrobots.prochat.presentation.viewmodels.AuthenticationViewModel;

import androidx.lifecycle.LifecycleOwner;

public abstract class SignUpAuthentication {


    private static final int PASSWORD_MIN_LENGTH = 6;
    private boolean signUpWithEmail;
    private Context activity;
    private AuthenticationViewModel authenticationViewModel;

    View mainLayout;
    EditText mobileEmailEditText;
    EditText usernameEditText;
    EditText passwordEditText;
    Button mobileSignUpButton;
    Button emailSignUpButton;
    Button signUpButton;
    CheckBox acceptTermsCheckbox;
    TextView mobileCountryCodeTextView;

    public void initializeViews(Context activity, AuthenticationViewModel authenticationViewModel,
                                View mainLayout,
                                EditText mobileEmailEditText,
                                EditText usernameEditText,
                                EditText passwordEditText,
                                Button mobileSignUpButton,
                                Button emailSignUpButton,
                                Button signUpButton,
                                CheckBox acceptTermsCheckbox,
                                TextView mobileCountryCodeTextView) {
        this.activity = activity;
        this.authenticationViewModel = authenticationViewModel;
        this.mainLayout = mainLayout;
        this.mobileEmailEditText = mobileEmailEditText;
        this.usernameEditText = usernameEditText;
        this.passwordEditText = passwordEditText;
        this.mobileSignUpButton = mobileSignUpButton;
        this.emailSignUpButton = emailSignUpButton;
        this.signUpButton = signUpButton;
        this.acceptTermsCheckbox = acceptTermsCheckbox;
        this.mobileCountryCodeTextView = mobileCountryCodeTextView;
    }

    public void initializeObservers(LifecycleOwner lifecycleOwner) {
        observeSigningUpWithEmail(lifecycleOwner);
        observeError(lifecycleOwner);
    }

    private void observeSigningUpWithEmail(LifecycleOwner lifecycleOwner) {
        authenticationViewModel.observeAuthenticateState().observe(lifecycleOwner, success -> {
            if (success) {
                signUpWithEmailSuccessAction();
            } else {
                signUpWithEmailErrorAction("Can't verify your email");
            }
        });
    }

    protected abstract void signUpWithEmailSuccessAction();

    protected abstract void signUpWithEmailErrorAction(String error);

    private void observeError(LifecycleOwner lifecycleOwner) {
        authenticationViewModel.observeErrorState().observe(lifecycleOwner, this::signUpWithEmailErrorAction);
    }

    public void onSignUpClicked() {
        String mobileOrEmail = mobileEmailEditText.getText().toString();
        String userName = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (signUpWithEmail) {
            authenticationViewModel.signUpWithEmail(mobileOrEmail, userName, password);
        } else {
            String phoneNumber = "+20" + (mobileOrEmail.startsWith("0") ? mobileOrEmail.replaceFirst("0", "") : mobileOrEmail);
            goToVerificationScreen(phoneNumber, userName, password);
        }
    }

    protected abstract void goToVerificationScreen(String phoneNumber, String userName, String password);

    public void onSignInClicked() {
        goToLoginScreen();
    }

    protected abstract void goToLoginScreen();

    public void onSignUpWithEmailClicked() {
        signUpWithEmail = true;
        //set email selected
        emailSignUpButton.setBackground(activity.getResources().getDrawable(R.drawable.button_tab_selected));
        emailSignUpButton.setTextColor(activity.getResources().getColor(R.color.White));
        emailSignUpButton.setCompoundDrawableTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.White)));

        //set mobile unselected
        mobileSignUpButton.setBackground(null);
        mobileSignUpButton.setTextColor(activity.getResources().getColor(R.color.DarkGray));
        mobileSignUpButton.setCompoundDrawableTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.DarkGray)));

        mobileEmailEditText.setHint(R.string.email);
        mobileEmailEditText.setText("");
        mobileEmailEditText.setError(null);
        mobileEmailEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        mobileCountryCodeTextView.setVisibility(View.GONE);
    }

    public void onSignUpWithMobileClicked() {
        signUpWithEmail = false;
        //set mobile selected
        mobileSignUpButton.setBackground(activity.getResources().getDrawable(R.drawable.button_tab_selected));
        mobileSignUpButton.setTextColor(activity.getResources().getColor(R.color.White));
        mobileSignUpButton.setCompoundDrawableTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.White)));

        //set email unselected
        emailSignUpButton.setBackground(null);
        emailSignUpButton.setTextColor(activity.getResources().getColor(R.color.DarkGray));
        emailSignUpButton.setCompoundDrawableTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.DarkGray)));

        mobileEmailEditText.setHint("Mobile");
        mobileEmailEditText.setText("");
        mobileEmailEditText.setError(null);
        mobileEmailEditText.setInputType(InputType.TYPE_CLASS_PHONE);
        mobileCountryCodeTextView.setVisibility(View.VISIBLE);
    }

    public void onMobileEmailTextChanged(CharSequence s, int start, int count, int after) {
        String userName = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (signUpWithEmail) {
            if (!Utils.isEmailValid(s.toString())) {
                mobileEmailEditText.setError("The email is not valid");
                signUpButton.setBackground(activity.getResources().getDrawable(R.drawable.dimmed_button_bg));
                return;
            }
        } else {
            if (!Utils.isPhoneValid(s.toString())) {
                mobileEmailEditText.setError("The phone number is not valid");
                signUpButton.setBackground(activity.getResources().getDrawable(R.drawable.dimmed_button_bg));
                return;
            }
        }

        if (userName.length() > 0 && password.length() > PASSWORD_MIN_LENGTH) {
            signUpButton.setBackground(activity.getResources().getDrawable(R.drawable.active_button_bg));
            signUpButton.setEnabled(true);
        } else {
            signUpButton.setBackground(activity.getResources().getDrawable(R.drawable.dimmed_button_bg));
            signUpButton.setEnabled(false);
        }

    }

    public void onUserNameTextChanged(CharSequence s, int start, int count, int after) {
        String mobileEmail = mobileEmailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        boolean mobileEmailValid = signUpWithEmail ? Utils.isEmailValid(mobileEmail) : Utils.isPhoneValid(mobileEmail);
        if (mobileEmailValid && s.length() > 0 && password.length() >= PASSWORD_MIN_LENGTH) {
            signUpButton.setBackground(activity.getResources().getDrawable(R.drawable.active_button_bg));
            signUpButton.setEnabled(true);
        } else {
            signUpButton.setBackground(activity.getResources().getDrawable(R.drawable.dimmed_button_bg));
            signUpButton.setEnabled(false);
        }
    }

    public void onPasswordTextChanged(CharSequence s, int start, int count, int after) {
        String mobileEmail = mobileEmailEditText.getText().toString();
        String userName = usernameEditText.getText().toString();

        boolean mobileEmailValid = signUpWithEmail ? Utils.isEmailValid(mobileEmail) : Utils.isPhoneValid(mobileEmail);
        if (mobileEmailValid && userName.length() > 0 && s.length() >= PASSWORD_MIN_LENGTH) {
            signUpButton.setBackground(activity.getResources().getDrawable(R.drawable.active_button_bg));
            signUpButton.setEnabled(true);
        } else {
            signUpButton.setBackground(activity.getResources().getDrawable(R.drawable.dimmed_button_bg));
            signUpButton.setEnabled(false);
        }
    }
}
