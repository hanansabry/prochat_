package com.egrobots.prochat.presentation.screens.home.tabs.groups.groupdetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.egrobots.prochat.R;
import com.egrobots.prochat.model.Member;
import com.egrobots.prochat.model.Message;
import com.egrobots.prochat.presentation.adapters.GroupDetailsMessagesAdapter;
import com.egrobots.prochat.presentation.adapters.RecentMembersAdapter;
import com.egrobots.prochat.presentation.screens.home.tabs.groups.creategroup.AddMembersToGroupFragmentDialog;
import com.egrobots.prochat.utils.AppBarStateChangeListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GroupDetailsActivity extends AppCompatActivity {

    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.collapsed_header_layout)
    View collapsedHeaderLayout;
    @BindView(R.id.members_recycler_view)
    RecyclerView membersRecyclerView;
    @BindView(R.id.messages_recycler_view)
    RecyclerView messagesRecyclerView;
    @BindView(R.id.times_group_details_layout)
    View groupTimesLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_details);
        ButterKnife.bind(this);
        //set scrolling behavior
        setScrollingBehavior();
        setupMembersRecyclerView();
        setupMessagesRecyclerView();
    }

    @OnClick(R.id.add_members_button)
    public void onAddMembersClicked() {
        AddMembersToGroupFragmentDialog dialog = AddMembersToGroupFragmentDialog.newInstance();
        dialog.show(getSupportFragmentManager(), AddMembersToGroupFragmentDialog.TAG);
    }

    @OnClick(R.id.more_times_button)
    public void onMoreTimesClicked() {
        Toast.makeText(this, "More is clicked", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.view_all_members_button)
    public void onViewAllMembersClicked() {
        startActivity(new Intent(this, GroupDetailsMembersActivity.class));
    }

    @OnClick(R.id.view_all_messages_button)
    public void onViewAllMessagesClicked() {
        startActivity(new Intent(this, GroupDetailsMessagesActivity.class));
    }

    private void setScrollingBehavior() {
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, AppBarStateChangeListener.State state) {
                if (state == State.COLLAPSED) {
                    collapsedHeaderLayout.setVisibility(View.VISIBLE);
                } else {
                    collapsedHeaderLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setupMembersRecyclerView() {
        List<Member> memberList = new ArrayList<>();
        memberList.add(new Member("Hanan Sabry"));
        memberList.add(new Member("Ahmed Morsi"));
        memberList.add(new Member("Ahmed Morsi"));
        RecentMembersAdapter adapter = new RecentMembersAdapter(memberList);
        membersRecyclerView.setAdapter(adapter);
        membersRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
    }

    private void setupMessagesRecyclerView() {
        List<Message> messageList = new ArrayList<>();
        messageList.add(new Message("Hi Ahmed, I am texting you because we need to do somd lk", Calendar.getInstance().getTimeInMillis(), true, true));
        messageList.add(new Message("Hi", Calendar.getInstance().getTimeInMillis(), true, true));
        messageList.add(new Message("Ok, Thanks.", Calendar.getInstance().getTimeInMillis(), true, true));
        messageList.add(new Message("Hi", Calendar.getInstance().getTimeInMillis(), true, true));

        GroupDetailsMessagesAdapter adapter = new GroupDetailsMessagesAdapter("Ahmed Morsi", messageList, null);
        messagesRecyclerView.setAdapter(adapter);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}