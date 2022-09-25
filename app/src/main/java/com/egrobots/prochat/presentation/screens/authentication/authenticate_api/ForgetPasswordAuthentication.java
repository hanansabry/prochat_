package com.egrobots.prochat.presentation.screens.authentication.authenticate_api;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.egrobots.prochat.R;
import com.egrobots.prochat.utils.Utils;
import com.egrobots.prochat.presentation.viewmodels.AuthenticationViewModel;

import androidx.lifecycle.LifecycleOwner;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class ForgetPasswordAuthentication {

    private AuthenticationViewModel authenticationViewModel;
    private Context activity;
    private TextView mainText;
    private TextView descText;
    private TextView receiveMailText;
    private ImageView bg;
    private ImageView secondaryIcon;
    private EditText emailEditText;
    private Button sendButton;

    public void initializeViews(AuthenticationViewModel authenticationViewModel,
                                        Context activity,
                                        TextView mainText,
                                        TextView descText,
                                        TextView receiveMailText,
                                        ImageView bg,
                                        ImageView secondaryIcon,
                                        EditText emailEditText,
                                        Button sendButton) {
        this.authenticationViewModel = authenticationViewModel;
        this.activity = activity;
        this.mainText = mainText;
        this.descText = descText;
        this.receiveMailText = receiveMailText;
        this.bg = bg;
        this.secondaryIcon = secondaryIcon;
        this.emailEditText = emailEditText;
        this.sendButton = sendButton;
    }

    public void initializeObservers(LifecycleOwner lifecycleOwner) {
        observeResetPasswordSentState(lifecycleOwner);
        observeErrorState(lifecycleOwner);
    }

    private void observeResetPasswordSentState(LifecycleOwner lifecycleOwner) {
        authenticationViewModel.observeResetPasswordSentState().observe(lifecycleOwner, success -> {
            Toast.makeText(activity, "Check your email", Toast.LENGTH_SHORT).show();
        });
    }

    private void observeErrorState(LifecycleOwner lifecycleOwner) {
        authenticationViewModel.observeErrorState().observe(lifecycleOwner, error -> {
            Toast.makeText(activity, error, Toast.LENGTH_SHORT).show();
        });
    }

    @OnClick(R.id.send_button)
    public void onSendClicked() {
        mainText.setText(R.string.check_your_email);
        descText.setText(R.string.check_your_email_msg);
        receiveMailText.setVisibility(View.VISIBLE);
        sendButton.setVisibility(View.GONE);
        emailEditText.setVisibility(View.INVISIBLE);
        if (bg!=null) bg.setImageDrawable(activity.getDrawable(R.drawable.check_your_mail_bg));
        if (secondaryIcon!= null) secondaryIcon.setImageDrawable(activity.getDrawable(R.drawable.emailsent_vector));

        String email = emailEditText.getText().toString();
        authenticationViewModel.sendPasswordResetEmail(email);
    }

    @OnTextChanged(R.id.email_edit_text)
    public void onEmailTextChanged(CharSequence s, int start, int count, int after) {
        if (Utils.isEmailValid(s.toString())) {
            emailEditText.setError(null);
            sendButton.setEnabled(true);
            sendButton.setBackground(activity.getDrawable(R.drawable.active_button_bg));
        } else {
            emailEditText.setError("The email is not valid");
            sendButton.setEnabled(false);
            sendButton.setBackground(activity.getDrawable(R.drawable.dimmed_button_bg));
        }
    }
}
