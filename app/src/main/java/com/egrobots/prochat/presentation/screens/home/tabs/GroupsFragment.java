package com.egrobots.prochat.presentation.screens.home.tabs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.egrobots.prochat.R;
import com.egrobots.prochat.callbacks.OnGroupSelectedCallback;
import com.egrobots.prochat.model.Group;
import com.egrobots.prochat.presentation.adapters.GroupsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupsFragment extends Fragment {

    @BindView(R.id.groups_recycler_view)
    RecyclerView groupsRecyclerView;

    public GroupsFragment() {
        // Required empty public constructor
    }

    public static GroupsFragment newInstance() {
        return new GroupsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_groups, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Group> groupList = new ArrayList<>();
        groupList.add(new Group("General Group", "General", false));
        groupList.add(new Group("Patients Questions", "General", false));
        groupList.add(new Group("Hospital Team", "Private", true));
        groupList.add(new Group("Friends", "Private", true));
        groupList.add(new Group("Book", "General", false));
        GroupsAdapter groupsAdapter = new GroupsAdapter(groupList, group -> {
            Toast.makeText(getContext(), group.getGroupName(), Toast.LENGTH_SHORT).show();
        });

        groupsRecyclerView.setAdapter(groupsAdapter);
        groupsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}