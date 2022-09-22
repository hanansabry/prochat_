package com.egrobots.prochat.presentation.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egrobots.prochat.R;
import com.egrobots.prochat.presentation.adapters.UserGroupsFragmentAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProfileContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileContentFragment extends Fragment {

    @BindView(R.id.group_tabs_viewpager)
    ViewPager2 groupTabsViewPager;
    @BindView(R.id.groups_tablayout)
    TabLayout groupTabsLayout;

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
        //setup groups tab
        setupGroupTabs();
        return view;
    }

    private void setupGroupTabs() {
        UserGroupsFragmentAdapter groupsFragmentAdapter = new UserGroupsFragmentAdapter(getParentFragmentManager(), getLifecycle());
        groupTabsViewPager.setAdapter(groupsFragmentAdapter);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(groupTabsLayout, groupTabsViewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText("All");
            } else if (position == 1) {
                tab.setText("My Groups");
            } else if (position == 2) {
                tab.setText("Group 1");
            } else if (position == 3) {
                tab.setText("Group 2");
            } else if (position == 4) {
                tab.setText("Group 3");
            } else if (position == 5) {
                tab.setText("Group 4");
            } else if (position == 6) {
                tab.setText("Group 5");
            }
        });
        tabLayoutMediator.attach();
    }
}