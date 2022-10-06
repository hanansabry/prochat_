package com.egrobots.prochat.presentation.screens.home.tabs.groups.groupdetails.dialogs;

import android.content.Context;

import com.egrobots.prochat.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageSelectedActionsDialog extends BottomSheetDialog {

    public MessageSelectedActionsDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.message_selected_actions_dialog);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.delete_message_button)
    public void onDeleteMemberClicked() {
        RemoveMembersDialog removeMembersDialog = new RemoveMembersDialog(getContext());
        removeMembersDialog.show();
    }
}
