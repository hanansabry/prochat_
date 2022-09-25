package com.egrobots.prochat.presentation.screens.authentication.authenticate_api;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.egrobots.prochat.R;
import com.egrobots.prochat.presentation.screens.search.SearchForFriendsBottomSheetDialog;
import com.egrobots.prochat.utils.Utils;
import com.egrobots.prochat.presentation.viewmodels.AuthenticationViewModel;

import androidx.lifecycle.LifecycleOwner;
import butterknife.OnTextChanged;

public abstract class LoginAuthentication {

    private static final int PASSWORD_MIN_LENGTH = 6;
    private AuthenticationViewModel authenticationViewModel;
    private Context activity;
    private EditText mobileEmailEditText;
    private EditText passwordEditText;
    private Button loginButton;

    public void initializeViews(Context activity,
                               AuthenticationViewModel authenticationViewModel,
                               EditText mobileEmailEditText,
                               EditText passwordEditText,
                               Button loginButton) {
        this.activity = activity;
        this.authenticationViewModel = authenticationViewModel;
        this.mobileEmailEditText = mobileEmailEditText;
        this.passwordEditText = passwordEditText;
        this.loginButton = loginButton;
    }

    public void initializeObservers(LifecycleOwner lifecycleOwner) {
        observeSignIn(lifecycleOwner);
        observeError(lifecycleOwner);
    }

    private void observeSignIn(LifecycleOwner lifecycleOwner) {
        authenticationViewModel.observeAuthenticateState().observe(lifecycleOwner, success -> {
            if (success) {
                loginSuccessAction();
            } else {
                loginErrorAction("invalid credentials");
            }
        });
    }

    protected abstract void loginSuccessAction();

    protected abstract void loginErrorAction(String error);


    private void observeError(LifecycleOwner lifecycleOwner) {
        authenticationViewModel.observeErrorState().observe(lifecycleOwner, this::loginErrorAction);
    }

    public void onSearchEditTextFocusChange(View v, boolean hasFocus) {
        SearchForFriendsBottomSheetDialog searchDialog = new SearchForFriendsBottomSheetDialog(activity);
        if (hasFocus) {
            searchDialog.show();
        } else {
            searchDialog.dismiss();
        }
    }

    public void onLoginClicked() {
        String emailOrPhone = mobileEmailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (Utils.isEmailValid(emailOrPhone)) {
            //login with email
            authenticationViewModel.signInWithEmail(emailOrPhone, password);
        } else if (Utils.isPhoneValid(emailOrPhone)) {
            authenticationViewModel.signInWithPhone(emailOrPhone, password);
        } else {
//            Toast.makeText(this, "Not valid email or phone", Toast.LENGTH_SHORT).show();
            loginErrorAction("Not valid email or phone");
        }
    }

    public void onForgotPasswordClicked() {
        goToForgetPasswordScreen();
    }

    protected abstract void goToForgetPasswordScreen();
    protected abstract void goToSignUpScreen();

    public void onSignUpClicked() {
        goToSignUpScreen();
    }

    public void onEmailTextChanged(CharSequence s, int start, int count, int after) {
        if (s.length() == 0) {
            mobileEmailEditText.setError(null);
            return;
        }
        //validate email
        if (!Utils.isEmailValid(s.toString()) && !Utils.isPhoneValid(s.toString())) {
            mobileEmailEditText.setError("Not valid email or phone");
            loginButton.setBackground(activity.getDrawable(R.drawable.dimmed_button_bg));
            return;
        }
        String password = passwordEditText.getText().toString();
        if (password.length() >= PASSWORD_MIN_LENGTH) {
            loginButton.setEnabled(true);
            loginButton.setBackground(activity.getDrawable(R.drawable.active_button_bg));
        } else {
            loginButton.setEnabled(false);
            loginButton.setBackground(activity.getDrawable(R.drawable.dimmed_button_bg));
        }
    }

    @OnTextChanged(R.id.password_edit_text)
    public void onPasswordTextChanged(CharSequence s, int start, int count, int after) {
        String emailPhone = mobileEmailEditText.getText().toString();
        if ((Utils.isEmailValid(emailPhone) || Utils.isPhoneValid(emailPhone)) && s.length() >= PASSWORD_MIN_LENGTH) {
            loginButton.setEnabled(true);
            loginButton.setBackground(activity.getDrawable(R.drawable.active_button_bg));
        } else {
            loginButton.setEnabled(false);
            loginButton.setBackground(activity.getDrawable(R.drawable.dimmed_button_bg));
        }
    }
}
