package com.egrobots.prochat.presentation.screens.home.tabs.groups;

import android.content.Context;

import com.egrobots.prochat.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;

public class DeleteGroupDialog extends BottomSheetDialog {
    public DeleteGroupDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.delete_group_dialog);
    }
}
