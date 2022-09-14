package com.egrobots.prochat.presentation.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import dagger.android.support.DaggerAppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.egrobots.prochat.R;
import com.egrobots.prochat.di.ViewModelProviderFactory;
import com.egrobots.prochat.utils.Utils;
import com.egrobots.prochat.viewmodels.AuthenticationViewModel;

import javax.inject.Inject;

public class ForgotPasswordActivity extends DaggerAppCompatActivity {

    private AuthenticationViewModel authenticationViewModel;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);

        authenticationViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(AuthenticationViewModel.class);
        authenticationViewModel.observeResetPasswordSentState().observe(this, success -> {
            Toast.makeText(ForgotPasswordActivity.this, "Check your email", Toast.LENGTH_SHORT).show();
        });
        authenticationViewModel.observeErrorState().observe(this, error -> {
            Toast.makeText(ForgotPasswordActivity.this, error, Toast.LENGTH_SHORT).show();
        });
    }

    @OnClick(R.id.send_button)
    public void onSendClicked() {
        mainText.setText(R.string.check_your_email);
        descText.setText(R.string.check_your_email_msg);
        receiveMailText.setVisibility(View.VISIBLE);
        sendButton.setVisibility(View.GONE);
        emailEditText.setVisibility(View.INVISIBLE);
        bg.setImageDrawable(getResources().getDrawable(R.drawable.check_your_mail_bg));
        secondaryIcon.setImageDrawable(getResources().getDrawable(R.drawable.emailsent_vector));

        String email = emailEditText.getText().toString();
        authenticationViewModel.sendPasswordResetEmail(email);
    }

    @OnTextChanged(R.id.email_edit_text)
    public void onEmailTextChanged(CharSequence s, int start, int count, int after) {
        if (Utils.isEmailValid(s.toString())) {
            emailEditText.setError(null);
            sendButton.setEnabled(true);
            sendButton.setBackground(getResources().getDrawable(R.drawable.active_button_bg));
        } else {
            emailEditText.setError("The email is not valid");
            sendButton.setEnabled(false);
            sendButton.setBackground(getResources().getDrawable(R.drawable.dimmed_button_bg));
        }
    }
}