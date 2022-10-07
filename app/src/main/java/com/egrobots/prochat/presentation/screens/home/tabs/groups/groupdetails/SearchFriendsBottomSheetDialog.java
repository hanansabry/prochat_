package com.egrobots.prochat.presentation.screens.home.tabs.groups.groupdetails;

import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.egrobots.prochat.R;
import com.egrobots.prochat.model.Member;
import com.egrobots.prochat.presentation.adapters.MembersAdapter;
import com.egrobots.prochat.utils.Constants;
import com.egrobots.prochat.utils.CustomBottomSheetFragment;

import java.util.ArrayList;
import java.util.List;

public class SearchFriendsBottomSheetDialog extends CustomBottomSheetFragment {

    @BindView(R.id.members_recyclerview)
    RecyclerView membersRecyclerView;
    @BindView(R.id.deactivated_search_edit_text)
    EditText deactivatedSearchEditText;
    @BindView(R.id.actions_layout)
    View actions_layout;
    @BindView(R.id.activated_search_edit_text)
    View activatedSearchEditText;
    @BindView(R.id.main_layout)
    View mainLayout;

    public SearchFriendsBottomSheetDialog() {
        // Required empty public constructor
    }

    public static SearchFriendsBottomSheetDialog newInstance() {
        return new SearchFriendsBottomSheetDialog();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.search_friends_dialog, container, false);
        ButterKnife.bind(this, view);
        setMainLayout(mainLayout, Constants.DIALOG_HEIGHT_PERCENT);
        setMembersRecyclerView();
        deactivatedSearchEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    actions_layout.setVisibility(View.INVISIBLE);
                    activatedSearchEditText.setVisibility(View.VISIBLE);
                    activatedSearchEditText.requestFocus();
                    //show keypad
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                }
            }
        });
        return view;
    }

    private void setMembersRecyclerView() {
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
        memberList.add(new Member("Ahmed Morsi", "#at9802"));
        memberList.add(new Member("Ahmed Morsi", "#at9802"));
        memberList.add(new Member("Ahmed Morsi", "#at9802"));
        memberList.add(new Member("Ahmed Morsi", "#at9802"));
        MembersAdapter adapter = new MembersAdapter(memberList, null);
        membersRecyclerView.setAdapter(adapter);
        membersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}