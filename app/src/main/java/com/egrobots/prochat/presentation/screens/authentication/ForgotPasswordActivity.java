package com.egrobots.prochat.presentation.screens.authentication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.egrobots.prochat.R;
import com.egrobots.prochat.di.ViewModelProviderFactory;
import com.egrobots.prochat.presentation.screens.authentication.authenticate_api.ForgetPasswordAuthentication;
import com.egrobots.prochat.presentation.viewmodels.AuthenticationViewModel;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import dagger.android.support.DaggerAppCompatActivity;

public class ForgotPasswordActivity extends DaggerAppCompatActivity {

    @Inject
    ViewModelProviderFactory providerFactory;
    @BindView(R.id.forgot_password_text)
    TextView mainText;
    @BindView(R.id.forgot_password_desc)
    TextView descText;
    @BindView(R.id.receive_mail_text)
    TextView receiveMailText;
    @BindView(R.id.forgot_password_bg)
    ImageView bg;
    @BindView(R.id.secondary_icon_imageview)
    ImageView secondaryIcon;
    @BindView(R.id.email_edit_text)
    EditText emailEditText;
    @BindView(R.id.send_button)
    Button sendButton;

    private ForgetPasswordAuthentication forgetPasswordAuthentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);

        AuthenticationViewModel authenticationViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(AuthenticationViewModel.class);
        forgetPasswordAuthentication = new ForgetPasswordAuthentication();
        forgetPasswordAuthentication.initializeViews(authenticationViewModel,
                this,
                mainText,
                descText,
                receiveMailText,
                bg,
                secondaryIcon,
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