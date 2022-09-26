package com.egrobots.prochat.presentation.adapters;

import com.egrobots.prochat.model.Group;
import com.egrobots.prochat.presentation.screens.userprofile.groups.GroupChatsFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class UserGroupsFragmentAdapter extends FragmentStateAdapter {

    private List<Group> groupList;
    private boolean isHomeScreen;

    public UserGroupsFragmentAdapter(@NonNull FragmentManager fragmentManager,
                                     @NonNull Lifecycle lifecycle,
                                     List<Group> groupList,
                                     boolean isHomeScreen) {
        super(fragmentManager, lifecycle);
        this.groupList = groupList;
        this.isHomeScreen = isHomeScreen;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Group group = groupList.get(position);
        return GroupChatsFragment.newInstance(group.getGroupId(), group.getGroupName(), isHomeScreen);
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }
}
