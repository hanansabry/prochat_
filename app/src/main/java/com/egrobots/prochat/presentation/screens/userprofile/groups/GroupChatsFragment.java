package com.egrobots.prochat.presentation.screens.userprofile.groups;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egrobots.prochat.R;
import com.egrobots.prochat.di.ViewModelProviderFactory;
import com.egrobots.prochat.model.Chat;
import com.egrobots.prochat.presentation.adapters.GroupMessagesOutlineForAllMembersAdapter;
import com.egrobots.prochat.presentation.adapters.GroupMessagesOutlineForOneMemberAdapter;
import com.egrobots.prochat.callbacks.OnGroupSelectedCallback;
import com.egrobots.prochat.utils.Constants;
import com.egrobots.prochat.presentation.viewmodels.UserProfileViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupChatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupChatsFragment extends DaggerFragment {

    @BindView(R.id.group_messages_recycler_view)
    RecyclerView groupMessagesRecyclerView;
    @Inject
    ViewModelProviderFactory providerFactory;

    private OnGroupSelectedCallback onGroupSelectedCallback;
    private List<Chat> groupChats = new ArrayList<>();
    private String groupId;
    private String groupName;
    private boolean isHomeScreen;

    public GroupChatsFragment() {
        // Required empty public constructor
    }
    public static GroupChatsFragment newInstance(String groupId, String groupName, boolean isHomeScreen) {
        GroupChatsFragment groupChatsFragment = new GroupChatsFragment();
        Bundle args = new Bundle();
        args.putString(Constants.GROUP_ID, groupId);
        args.putString(Constants.GROUP_NAME, groupName);
        args.putBoolean(Constants.IS_HOME_SCREEN, isHomeScreen);
        groupChatsFragment.setArguments(args);
        return groupChatsFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onGroupSelectedCallback = (OnGroupSelectedCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentToActivity");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            groupId = getArguments().getString(Constants.GROUP_ID);
            groupName = getArguments().getString(Constants.GROUP_NAME);
            isHomeScreen = getArguments().getBoolean(Constants.IS_HOME_SCREEN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UserProfileViewModel userProfileViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(UserProfileViewModel.class);
        userProfileViewModel.getGroupChats(groupId);
        userProfileViewModel.observeGroupChats().observe(getViewLifecycleOwner(), groupChat -> {
            groupChats.add(groupChat);
        });
        userProfileViewModel.isGroupChatsRetrievingFinished().observe(getViewLifecycleOwner(), isFinished -> {
            if (isFinished) {
                if (isHomeScreen) {
                    GroupMessagesOutlineForAllMembersAdapter adapter2 = new GroupMessagesOutlineForAllMembersAdapter(groupName, groupChats, onGroupSelectedCallback);
                    groupMessagesRecyclerView.setAdapter(adapter2);
                    groupMessagesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                } else {
                    GroupMessagesOutlineForOneMemberAdapter adapter2 = new GroupMessagesOutlineForOneMemberAdapter(groupName, groupChats, onGroupSelectedCallback);
                    groupMessagesRecyclerView.setAdapter(adapter2);
                    groupMessagesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                }
            }
        });
    }

    @Override
    public void onDetach() {
        onGroupSelectedCallback = null;
        super.onDetach();
    }
}