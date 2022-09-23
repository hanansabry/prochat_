package com.egrobots.prochat.presentation.dialogs.authentication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.egrobots.prochat.R;
import com.egrobots.prochat.di.DaggerBottomSheetDialogFragment;
import com.egrobots.prochat.di.ViewModelProviderFactory;
import com.egrobots.prochat.presentation.authentication.MainActivity;
import com.egrobots.prochat.presentation.authentication.VerifyAccount;
import com.egrobots.prochat.presentation.authentication.authenticate_api.VerificationAuthentication;
import com.egrobots.prochat.utils.Constants;
import com.egrobots.prochat.viewmodels.AuthenticationViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class VerifyAccountBottomSheetDialog extends DaggerBottomSheetDialogFragment {

    public static final String TAG = "VerifyAccountBottomSheetDialog";

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
    private String phoneNumber;
    private String userName;
    private String password;
    private DialogVerificationAuthentication verificationAuthentication;

    public static VerifyAccountBottomSheetDialog newInstance(String phoneNumber, String userName, String password) {
        VerifyAccountBottomSheetDialog fragmentDialog = new VerifyAccountBottomSheetDialog();
        Bundle arguments = new Bundle();
        arguments.putString(Constants.PHONE, phoneNumber);
        arguments.putString(Constants.USER_NAME, userName);
        arguments.putString(Constants.PASSWORD, password);
        fragmentDialog.setArguments(arguments);
        return fragmentDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.verify_account_dialog, container, false);
        ButterKnife.bind(this, view);
        if (getArguments() != null) {
            phoneNumber = getArguments().getString(Constants.PHONE);
            userName = getArguments().getString(Constants.USER_NAME);
            password = getArguments().getString(Constants.PASSWORD);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        AuthenticationViewModel authenticationViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(AuthenticationViewModel.class);
        verificationAuthentication = new DialogVerificationAuthentication();
        verificationAuthentication.initializeViews(getActivity(),
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
        verificationAuthentication.onVerifyClicked(phoneNumber, userName, password);
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private class DialogVerificationAuthentication extends VerificationAuthentication {

        @Override
        protected void verificationSuccessAction() {
            Toast.makeText(getContext(), "Sign up successfully", Toast.LENGTH_LONG).show();
            dismiss();
        }

        @Override
        protected void verificationErrorAction(String error) {
            Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
        }
    }
}
