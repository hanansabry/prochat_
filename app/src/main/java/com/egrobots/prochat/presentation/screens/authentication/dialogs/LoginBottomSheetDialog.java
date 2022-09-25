package com.egrobots.prochat.presentation.screens.authentication.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.egrobots.prochat.R;
import com.egrobots.prochat.callbacks.OnUserAuthenticateCallback;
import com.egrobots.prochat.di.DaggerBottomSheetDialogFragment;
import com.egrobots.prochat.di.ViewModelProviderFactory;
import com.egrobots.prochat.presentation.screens.authentication.authenticate_api.LoginAuthentication;
import com.egrobots.prochat.presentation.viewmodels.AuthenticationViewModel;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class LoginBottomSheetDialog extends DaggerBottomSheetDialogFragment {

    public static final String TAG = "LoginBottomSheetDialog";

    private DialogLoginAuthentication loginAuthentication;
    private OnUserAuthenticateCallback onUserAuthenticateCallback;

    @Inject
    ViewModelProviderFactory providerFactory;
    @BindView(R.id.mobile_email_edit_text)
    EditText mobileEmailEditText;
    @BindView(R.id.password_edit_text)
    EditText passwordEditText;
    @BindView(R.id.login_button)
    Button loginButton;

    public static LoginBottomSheetDialog newInstance() {
        return new LoginBottomSheetDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_bottom_dialog, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AuthenticationViewModel authenticationViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(AuthenticationViewModel.class);
        loginAuthentication = new DialogLoginAuthentication();
        loginAuthentication.initializeViews(getContext(),
                authenticationViewModel,
                mobileEmailEditText,
                passwordEditText,
                loginButton);
        loginAuthentication.initializeObservers(this);
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

    @OnClick(R.id.close_dialog_button)
    public void onCloseDialogClicked() {
        dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onUserAuthenticateCallback = (OnUserAuthenticateCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentToActivity");
        }
    }

    @Override
    public void onDetach() {
        onUserAuthenticateCallback = null;
        super.onDetach();
    }

    private class DialogLoginAuthentication extends LoginAuthentication {

        @Override
        protected void loginSuccessAction() {
            Toast.makeText(getContext(), "Login successfully", Toast.LENGTH_SHORT).show();
            dismiss();
            onUserAuthenticateCallback.onUserLoginSuccessfully();
        }

        @Override
        protected void loginErrorAction(String error) {
            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void goToForgetPasswordScreen() {
            ForgotPasswordBottomSheetDialog forgotPasswordDialog = ForgotPasswordBottomSheetDialog.newInstance();
            forgotPasswordDialog.show(getParentFragmentManager(), ForgotPasswordBottomSheetDialog.TAG);
        }

        @Override
        protected void goToSignUpScreen() {
            SignUpBottomSheetDialog signUpDialog = SignUpBottomSheetDialog.newInstance();
            signUpDialog.show(getParentFragmentManager(), SignUpBottomSheetDialog.TAG);
        }
    }
}
