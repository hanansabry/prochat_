package com.egrobots.prochat.presentation.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egrobots.prochat.R;
import com.egrobots.prochat.di.ViewModelProviderFactory;
import com.egrobots.prochat.model.Group;
import com.egrobots.prochat.presentation.adapters.UserGroupsFragmentAdapter;
import com.egrobots.prochat.viewmodels.UserProfileViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProfileContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileContentFragment extends DaggerFragment {

    @BindView(R.id.group_tabs_viewpager)
    ViewPager2 groupTabsViewPager;
    @BindView(R.id.groups_tablayout)
    TabLayout groupTabsLayout;
    @Inject
    ViewModelProviderFactory providerFactory;
    private List<Group> groupList = new ArrayList<>();


    public UserProfileContentFragment() {
        // Required empty public constructor
    }

    public static UserProfileContentFragment newInstance() {
        return new UserProfileContentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile_content, container, false);
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
    }

    private void setupGroupTabs() {
        UserGroupsFragmentAdapter groupsFragmentAdapter
                = new UserGroupsFragmentAdapter(getParentFragmentManager(), getLifecycle(), groupList);
        groupTabsViewPager.setAdapter(groupsFragmentAdapter);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(groupTabsLayout, groupTabsViewPager, (tab, position) -> {
            tab.setText(groupList.get(position).getGroupName());
        });
        tabLayoutMediator.attach();
    }
}