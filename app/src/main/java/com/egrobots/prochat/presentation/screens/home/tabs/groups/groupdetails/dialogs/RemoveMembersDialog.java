package com.egrobots.prochat.presentation.screens.home.tabs.groups.groupdetails.dialogs;

import android.content.Context;

import com.egrobots.prochat.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;

public class RemoveMembersDialog extends BottomSheetDialog {
    public RemoveMembersDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.delete_members_dialog);
    }
}
