package com.egrobots.prochat.presentation.screens.group;

import android.os.Bundle;
import android.widget.TextView;

import com.egrobots.prochat.R;
import com.egrobots.prochat.callbacks.GroupMembersCallback;
import com.egrobots.prochat.model.Member;
import com.egrobots.prochat.presentation.adapters.MembersAdapter;
import com.egrobots.prochat.presentation.screens.group.dialogs.MemberSelectedActionsDialog;
import com.egrobots.prochat.presentation.screens.home.tabs.groups.creategroup.AddMembersToGroupFragmentDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GroupDetailsMembersActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.subtitle)
    TextView subtitle;
    @BindView(R.id.group_members_recyclerview)
    RecyclerView membersRecyclerView;

    private List<Member> selectedMembers = new ArrayList<>();
    private MemberSelectedActionsDialog memberSelectedActionsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_details_members);
        ButterKnife.bind(this);
        title.setText(getString(R.string.members));
        subtitle.setText("Group Name");

        setMembersRecyclerView();
    }

    private void setMembersRecyclerView() {
        List<Member> memberList = new ArrayList<>();
        memberList.add(new Member("Hanan Sabry", "#at9802"));
        memberList.add(new Member("Ahmed Morsi", "#at9802"));
        memberList.add(new Member("Ahmed Morsi", "#at9802"));
        memberList.add(new Member("Ahmed Morsi", "#at9802"));
        memberList.add(new Member("Ahmed Morsi", "#at9802"));
        memberList.add(new Member("Ahmed Morsi", "#at9802"));
        memberList.add(new Member("Ahmed Morsi", "#at9802"));
        memberList.add(new Member("Ahmed Morsi", "#at9802"));
        memberList.add(new Member("Ahmed Morsi", "#at9802"));
        memberList.add(new Member("Ahmed Morsi", "#at9802"));
        memberList.add(new Member("Ahmed Morsi", "#at9802"));
        memberList.add(new Member("Ahmed Morsi", "#at9802"));
        memberList.add(new Member("Ahmed Morsi", "#at9802"));
        MembersAdapter adapter = new MembersAdapter(memberList, new GroupMembersCallback() {
            @Override
            public void onMemberLongClicked(Member member) {
                selectedMembers.add(member);
                memberSelectedActionsDialog = new MemberSelectedActionsDialog(GroupDetailsMembersActivity.this);
                memberSelectedActionsDialog.show();
            }

            @Override
            public void onMemberClicked(Member member) {
                selectedMembers.remove(member);
                if (selectedMembers.isEmpty()) {
                    memberSelectedActionsDialog.dismiss();
                }
            }
        });
        membersRecyclerView.setAdapter(adapter);
        membersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @OnClick(R.id.add_members_button)
    public void onAddMembersButtonClicked() {
        AddMembersToGroupFragmentDialog dialog = AddMembersToGroupFragmentDialog.newInstance();
        dialog.show(getSupportFragmentManager(), AddMembersToGroupFragmentDialog.TAG);
    }
}