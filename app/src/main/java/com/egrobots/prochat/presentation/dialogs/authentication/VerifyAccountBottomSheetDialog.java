package com.egrobots.prochat.presentation.dialogs.authentication;

import android.content.Context;

import com.egrobots.prochat.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerifyAccountBottomSheetDialog extends BottomSheetDialog {
    public VerifyAccountBottomSheetDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.verify_account_dialog);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.close_dialog_button)
    public void onCloseDialogClicked() {
        dismiss();
    }
}
