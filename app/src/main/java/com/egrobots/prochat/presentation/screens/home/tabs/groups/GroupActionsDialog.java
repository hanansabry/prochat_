package com.egrobots.prochat.presentation.screens.home.tabs.groups;

import android.content.Context;

import com.egrobots.prochat.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GroupActionsDialog extends BottomSheetDialog {
    public GroupActionsDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.group_actions_dilaog);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.delete_group)
    public void onDeleteGroup() {
        DeleteGroupDialog dialog = new DeleteGroupDialog(getContext());
        dialog.show();
    }
}
