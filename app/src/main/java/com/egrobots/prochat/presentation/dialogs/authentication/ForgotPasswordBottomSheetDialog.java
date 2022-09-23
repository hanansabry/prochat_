package com.egrobots.prochat.presentation.dialogs.authentication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.egrobots.prochat.R;
import com.egrobots.prochat.di.DaggerBottomSheetDialogFragment;
import com.egrobots.prochat.di.ViewModelProviderFactory;
import com.egrobots.prochat.presentation.authentication.authenticate_api.ForgetPasswordAuthentication;
import com.egrobots.prochat.viewmodels.AuthenticationViewModel;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class ForgotPasswordBottomSheetDialog extends DaggerBottomSheetDialogFragment {

    public static final String TAG = "ForgotPasswordBottomSheetDialog";
    @Inject
    ViewModelProviderFactory providerFactory;
    @BindView(R.id.forgot_password_text)
    TextView mainText;
    @BindView(R.id.forgot_password_desc)
    TextView descText;
    @BindView(R.id.receive_mail_text)
    TextView receiveMailText;
    @BindView(R.id.email_edit_text)
    EditText emailEditText;
    @BindView(R.id.send_button)
    Button sendButton;

    private ForgetPasswordAuthentication forgetPasswordAuthentication;

    public static ForgotPasswordBottomSheetDialog newInstance() {
        return new ForgotPasswordBottomSheetDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forgot_password_dialog, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AuthenticationViewModel authenticationViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(AuthenticationViewModel.class);
        forgetPasswordAuthentication = new ForgetPasswordAuthentication();
        forgetPasswordAuthentication.initializeViews(authenticationViewModel,
                getActivity(),
                mainText,
                descText,
                receiveMailText,
                null,
                null,
                emailEditText,
                sendButton);
        forgetPasswordAuthentication.initializeObservers(this);
    }

    @OnClick(R.id.send_button)
    public void onSendClicked() {
        forgetPasswordAuthentication.onSendClicked();
    }

    @OnTextChanged(R.id.email_edit_text)
    public void onEmailTextChanged(CharSequence s, int start, int count, int after) {
        forgetPasswordAuthentication.onEmailTextChanged(s, start, count, after);
    }
}
