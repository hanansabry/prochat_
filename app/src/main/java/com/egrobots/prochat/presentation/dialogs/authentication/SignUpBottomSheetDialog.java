package com.egrobots.prochat.presentation.dialogs.authentication;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.egrobots.prochat.R;
import com.egrobots.prochat.di.DaggerBottomSheetDialogFragment;
import com.egrobots.prochat.di.ViewModelProviderFactory;
import com.egrobots.prochat.presentation.authentication.SignUpActivity;
import com.egrobots.prochat.presentation.authentication.authenticate_api.SignUpAuthentication;
import com.egrobots.prochat.viewmodels.AuthenticationViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

public class SignUpBottomSheetDialog extends DaggerBottomSheetDialogFragment {

    public static final String TAG = "SignUpBottomSheetDialog";

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

    public static SignUpBottomSheetDialog newInstance() {
        return new SignUpBottomSheetDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_dialog, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AuthenticationViewModel authenticationViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(AuthenticationViewModel.class);
        signUpAuthentication = new DialogSignUpAuthentication();
        signUpAuthentication.initializeViews(getContext(),
                authenticationViewModel,
                mainLayout,
                mobileEmailEditText,
                usernameEditText,
                passwordEditText,
                mobileSignUpButton,
                emailSignUpButton,
                signUpButton,
                acceptTermsCheckbox,
                mobileCountryCodeTextView,
                true);
        signUpAuthentication.initializeObservers(this);
    }

    @OnClick(R.id.signup_button)
    public void onSignUpClicked() {
        dismiss();
        signUpAuthentication.onSignUpClicked();
    }

    @OnClick(R.id.sign_in_textview)
    public void onSignInClicked() {
        dismiss();
        signUpAuthentication.onSignInClicked();
    }

    @OnClick(R.id.close_dialog_button)
    public void onCloseDialogClicked() {
        dismiss();
    }

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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private class DialogSignUpAuthentication extends SignUpAuthentication {

        @Override
        protected void goToVerificationScreen(String phoneNumber, String userName, String password) {
            VerifyAccountBottomSheetDialog verifyDialog
                    = VerifyAccountBottomSheetDialog.newInstance(phoneNumber, userName, password);
            verifyDialog.show(getParentFragmentManager(), VerifyAccountBottomSheetDialog.TAG);
        }

        @Override
        protected void goToLoginScreen() {
            LoginBottomSheetDialog loginDialog = LoginBottomSheetDialog.newInstance();
            loginDialog.show(getParentFragmentManager(), LoginBottomSheetDialog.TAG);
        }

        @Override
        protected void signUpWithEmailSuccessAction() {
            Toast.makeText(getContext(), "Email verification has been sent to your mail, Please check it.", Toast.LENGTH_SHORT).show();
            dismiss();
        }

        @Override
        protected void signUpWithEmailErrorAction(String error) {
            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
        }
    }
}
