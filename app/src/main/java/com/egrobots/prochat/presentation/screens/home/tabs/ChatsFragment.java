package com.egrobots.prochat.presentation.screens.home.tabs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egrobots.prochat.R;
import com.egrobots.prochat.di.ViewModelProviderFactory;
import com.egrobots.prochat.model.Group;
import com.egrobots.prochat.model.Member;
import com.egrobots.prochat.presentation.adapters.RecentMembersAdapter;
import com.egrobots.prochat.presentation.adapters.UserGroupsFragmentAdapter;
import com.egrobots.prochat.presentation.viewmodels.UserProfileViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ChatsFragment extends DaggerFragment {

    @BindView(R.id.group_tabs_viewpager)
    ViewPager2 groupTabsViewPager;
    @BindView(R.id.groups_tablayout)
    TabLayout groupTabsLayout;
    @BindView(R.id.recent_members_recycler_view)
    RecyclerView recentMemberRecyclerView;
    @Inject
    ViewModelProviderFactory providerFactory;
    private List<Group> groupList = new ArrayList<>();

    public ChatsFragment() {
        // Required empty public constructor
    }

    public static ChatsFragment newInstance() {
        ChatsFragment fragment = new ChatsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chats, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UserProfileViewModel userProfileViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(UserProfileViewModel.class);
        userProfileViewModel.getUserGroups("");
        userProfileViewModel.observeGroups().observe(getViewLifecycleOwner(), group -> {
            groupList.add(group);
        });
        userProfileViewModel.isGroupRetrievingFinished().observe(getViewLifecycleOwner(), finished -> {
            if (finished) {
                //setup groups tab
                setupGroupTabs();
            }
        });
        setupRecentMembersRecyclerView();
    }

    private void setupRecentMembersRecyclerView() {
        List<Member> memberList = new ArrayList<>();
        memberList.add(new Member("Hanan Sabry"));
        memberList.add(new Member("Ahmed Morsi"));
        memberList.add(new Member("Ahmed Morsi"));
        memberList.add(new Member("Ahmed Morsi"));
        memberList.add(new Member("Ahmed Morsi"));
        memberList.add(new Member("Ahmed Morsi"));
        memberList.add(new Member("Ahmed Morsi"));
        memberList.add(new Member("Ahmed Morsi"));
        memberList.add(new Member("Ahmed Morsi"));
        RecentMembersAdapter adapter = new RecentMembersAdapter(memberList);
        recentMemberRecyclerView.setAdapter(adapter);
        recentMemberRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
    }

    private void setupGroupTabs() {
        UserGroupsFragmentAdapter groupsFragmentAdapter
                = new UserGroupsFragmentAdapter(getParentFragmentManager(), getLifecycle(), groupList, true);
        groupTabsViewPager.setAdapter(groupsFragmentAdapter);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(groupTabsLayout, groupTabsViewPager, (tab, position) -> {
            tab.setText(groupList.get(position).getGroupName());
        });
        tabLayoutMediator.attach();
    }
}