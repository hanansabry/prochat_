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

import com.egrobots.prochat.R;
import com.egrobots.prochat.model.Member;
import com.egrobots.prochat.presentation.adapters.MembersAdapter;
import com.egrobots.prochat.presentation.adapters.RecentMembersAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MembersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MembersFragment extends Fragment {

    @BindView(R.id.members_recycler_view)
    RecyclerView membersRecyclerView;

    public MembersFragment() {
        // Required empty public constructor
    }

    public static MembersFragment newInstance() {
        return new MembersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_members, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Member> memberList = new ArrayList<>();
        memberList.add(new Member("Hanan Sabry", "#at9802"));
        memberList.add(new Member("Ahmed Morsi", "#at9802"));
        memberList.add(new Member("Ahmed Morsi", "#at9802"));
        memberList.add(new Member("Ahmed Morsi", "#at9802"));
        memberList.add(new Member("Ahmed Morsi", "#at9802"));
        memberList.add(new Member("Ahmed Morsi", "#at9802"));
        memberList.add(new Member("Ahmed Morsi", "#at9802"));
        memberList.add(new Member("Ahmed Morsi", "#at9802"));
        memberList.add(new Member("Ahmed Morsi", "#at9802"));
        MembersAdapter adapter = new MembersAdapter(memberList);
        membersRecyclerView.setAdapter(adapter);
        membersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}