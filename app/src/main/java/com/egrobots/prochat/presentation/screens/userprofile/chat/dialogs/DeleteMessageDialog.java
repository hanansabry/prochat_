package com.egrobots.prochat.presentation.screens.userprofile.chat.dialogs;

import android.content.Context;

import com.egrobots.prochat.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;

public class DeleteMessageDialog extends BottomSheetDialog {
    public DeleteMessageDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.delete_message_dialog);
    }
}
