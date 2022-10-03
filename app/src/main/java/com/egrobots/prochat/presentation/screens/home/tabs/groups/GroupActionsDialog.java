package com.egrobots.prochat.presentation.screens.home.tabs.groups;

import android.content.Context;

import com.egrobots.prochat.R;
import com.egrobots.prochat.presentation.screens.home.tabs.groups.creategroup.AddMembersToGroupFragmentDialog;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GroupActionsDialog extends BottomSheetDialog {

    private AppCompatActivity context;
    public GroupActionsDialog(@NonNull Context context) {
        super(context);
        this.context = (AppCompatActivity) context;
        setContentView(R.layout.group_actions_dilaog);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.delete_group)
    public void onDeleteGroup() {
        DeleteGroupDialog dialog = new DeleteGroupDialog(getContext());
        dialog.show();
    }

    @OnClick(R.id.add_members_to_group)
    public void onAddMembersClicked() {
        AddMembersToGroupFragmentDialog dialog = AddMembersToGroupFragmentDialog.newInstance();
        dialog.show(context.getSupportFragmentManager(), AddMembersToGroupFragmentDialog.TAG);
    }
}
