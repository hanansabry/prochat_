package com.egrobots.prochat.presentation.screens.group.dialogs;

import android.content.Context;

import com.egrobots.prochat.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MemberSelectedActionsDialog extends BottomSheetDialog {

    public MemberSelectedActionsDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.member_selected_actions_dialog);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.delete_member_button)
    public void onDeleteMemberClicked() {
        RemoveMembersDialog removeMembersDialog = new RemoveMembersDialog(getContext());
        removeMembersDialog.show();
    }

    @OnClick(R.id.block_button)
    public void onBlockClicked() {
        BlockMemberDialog blockMemberDialog = new BlockMemberDialog(getContext());
        blockMemberDialog.show();
    }
}
