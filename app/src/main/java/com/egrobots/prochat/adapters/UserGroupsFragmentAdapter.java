package com.egrobots.prochat.adapters;

import com.egrobots.prochat.presentation.user.groups.GroupFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class UserGroupsFragmentAdapter extends FragmentStateAdapter {

    public UserGroupsFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return GroupFragment.newInstance();
    }

    @Override
    public int getItemCount() {
        return 6;
    }
}
