package com.egrobots.prochat.presentation.dialogs.authentication;

import android.content.Context;

import com.egrobots.prochat.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpBottomSheetDialog extends BottomSheetDialog {
    public SignUpBottomSheetDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.sign_up_dialog);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.signup_button)
    public void onSignUpClicked() {
        dismiss();
        new VerifyAccountBottomSheetDialog(getContext()).show();
    }

    @OnClick(R.id.sign_in_textview)
    public void onSignInClicked() {
        dismiss();
        new LoginBottomSheetDialog(getContext()).show();
    }

    @OnClick(R.id.close_dialog_button)
    public void onCloseDialogClicked() {
        dismiss();
    }
}
