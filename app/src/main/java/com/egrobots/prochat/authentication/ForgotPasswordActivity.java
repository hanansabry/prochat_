package com.egrobots.prochat.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.egrobots.prochat.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        TextView mainText = findViewById(R.id.forgot_password_text);
        TextView descText = findViewById(R.id.forgot_password_desc);
        TextView receiveMailText = findViewById(R.id.receive_mail_text);
        ImageView bg = findViewById(R.id.forgot_password_bg);
        ImageView secondaryIcon = findViewById(R.id.secondary_icon_imageview);
        EditText emailEditText = findViewById(R.id.email_edit_text);
        Button sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainText.setText("Check Your Email");
                descText.setText("A message has been sent to your email, check the message so you can to change your password.");
                receiveMailText.setVisibility(View.VISIBLE);
                sendButton.setVisibility(View.GONE);
                emailEditText.setVisibility(View.INVISIBLE);
                bg.setImageDrawable(getResources().getDrawable(R.drawable.check_your_mail_bg));
                secondaryIcon.setImageDrawable(getResources().getDrawable(R.drawable.emailsent_vector));
            }
        });
    }
}