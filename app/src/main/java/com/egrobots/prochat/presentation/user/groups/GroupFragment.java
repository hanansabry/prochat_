package com.egrobots.prochat.presentation.user.groups;

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
import com.egrobots.prochat.presentation.adapters.GroupMessagesOutlineAdapter;
import com.egrobots.prochat.callbacks.OnGroupSelectedCallback;
import com.egrobots.prochat.model.GroupChatOutline;
import com.egrobots.prochat.utils.Constants;
import com.egrobots.prochat.viewmodels.UserProfileViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupFragment extends DaggerFragment {

    @BindView(R.id.group_messages_recycler_view)
    RecyclerView groupMessagesRecyclerView;
    @Inject
    ViewModelProviderFactory providerFactory;

    private OnGroupSelectedCallback onGroupSelectedCallback;
    private List<Chat> groupChats = new ArrayList<>();
    private String groupId;
    private String groupName;

    public GroupFragment() {
        // Required empty public constructor
    }
    public static GroupFragment newInstance(String groupId, String groupName) {
        GroupFragment groupFragment = new GroupFragment();
        Bundle args = new Bundle();
        args.putString(Constants.GROUP_ID, groupId);
        args.putString(Constants.GROUP_NAME, groupName);
        groupFragment.setArguments(args);
        return groupFragment;
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
                GroupMessagesOutlineAdapter adapter2 = new GroupMessagesOutlineAdapter(groupName, groupChats, onGroupSelectedCallback);
                groupMessagesRecyclerView.setAdapter(adapter2);
                groupMessagesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });
    }

    @Override
    public void onDetach() {
        onGroupSelectedCallback = null;
        super.onDetach();
    }
}