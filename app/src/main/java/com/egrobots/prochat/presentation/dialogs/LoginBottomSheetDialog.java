package com.egrobots.prochat.presentation.dialogs;

import android.content.Context;

import com.egrobots.prochat.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginBottomSheetDialog extends BottomSheetDialog {
    public LoginBottomSheetDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.login_bottom_dialog);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.sign_up_textview)
    public void onSignUpClicked() {
        dismiss();
        new SignUpBottomSheetDialog(getContext()).show();
    }

    @OnClick(R.id.close_dialog_button)
    public void onCloseDialogClicked() {
        dismiss();
    }
}
