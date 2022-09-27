package com.egrobots.prochat.presentation.screens.home.tabs.account;

import android.content.Context;

import com.egrobots.prochat.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;

public class ShareAccountDialog extends BottomSheetDialog {
    public ShareAccountDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.share_account_dilaog);
    }
}
