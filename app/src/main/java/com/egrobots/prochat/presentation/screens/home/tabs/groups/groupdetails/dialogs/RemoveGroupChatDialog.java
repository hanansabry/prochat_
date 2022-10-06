package com.egrobots.prochat.presentation.screens.home.tabs.groups.groupdetails.dialogs;

import android.content.Context;

import com.egrobots.prochat.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;

public class RemoveGroupChatDialog extends BottomSheetDialog {
    public RemoveGroupChatDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.delete_group_chat_dialog);
    }
}
