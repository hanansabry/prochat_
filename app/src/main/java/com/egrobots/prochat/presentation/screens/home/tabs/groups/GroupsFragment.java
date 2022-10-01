package com.egrobots.prochat.presentation.screens.home.tabs.groups;

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
import android.widget.TextView;
import android.widget.Toast;

import com.egrobots.prochat.R;
import com.egrobots.prochat.callbacks.OnGroupSelectedCallback;
import com.egrobots.prochat.model.Group;
import com.egrobots.prochat.presentation.adapters.GroupsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupsFragment extends Fragment {

    @BindView(R.id.groups_recycler_view)
    RecyclerView groupsRecyclerView;
    @BindView(R.id.group_number)
    TextView groupNumberTextView;
    @BindView(R.id.create_group_view)
    View createGroupView;

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
//        groupList.add(new Group("Friends", "Private", true));
//        groupList.add(new Group("Book", "General", false));
        GroupsAdapter groupsAdapter = new GroupsAdapter(groupList, new OnGroupSelectedCallback() {
            @Override
            public void onGroupSelected(Group group) {
                Toast.makeText(getContext(), group.getGroupName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onGroupActionsClicked(Group group) {
                GroupActionsDialog dialog = new GroupActionsDialog(getContext());
                dialog.show();
            }
        });
        groupsRecyclerView.setAdapter(groupsAdapter);
        groupsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        groupNumberTextView.setText(String.format(Locale.getDefault(), getString(R.string.group_number_srting), groupList.size()));
        //if groups are less than 5 >> add "Create New Group" view to the end of group list
        if (groupList.size() == 5) {
            createGroupView.setVisibility(View.GONE);
        } else {
            createGroupView.setVisibility(View.VISIBLE);
            createGroupView.setOnClickListener(v -> {
            });
        }
    }
}