package com.egrobots.prochat.presentation.screens.home.tabs.account;

import android.content.Context;

import com.egrobots.prochat.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;

public class ChangeLanguageBottomSheetDialog extends BottomSheetDialog {
    public ChangeLanguageBottomSheetDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.change_language_dialog);
    }
}
