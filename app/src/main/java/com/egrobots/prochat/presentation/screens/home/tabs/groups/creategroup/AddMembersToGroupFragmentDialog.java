package com.egrobots.prochat.presentation.screens.home.tabs.groups.creategroup;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.egrobots.prochat.R;
import com.egrobots.prochat.callbacks.OnSelectMemberToAddToGroupCallback;
import com.egrobots.prochat.model.Member;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class AddMembersToGroupFragmentDialog extends BottomSheetDialogFragment {

    public static final String TAG = "AddMembersToGroupFragmentDialog";

    @BindView(R.id.members_recycler_view)
    RecyclerView membersRecyclerView;
    @BindView(R.id.selected_members_layout)
    View selectedMembersLayout;
    @BindView(R.id.selected_members_recycler_view)
    RecyclerView selectedMembersRecyclerView;
    @BindView(R.id.members_selected_text)
    TextView membersSelectedCountText;
    @BindView(R.id.remove_all_selected_members_button)
    TextView removeAllSelectedMembers;
    @BindView(R.id.deactivated_search_edit_text)
    EditText deactivatedSearchEditText;
    @BindView(R.id.search_layout_deactivated)
    View deactivatedSearchLayout;
    @BindView(R.id.search_layout_activated)
    View activatedSearchLayout;
    @BindView(R.id.activated_search_edit_text)
    EditText activatedSearchEditText;
    @BindView(R.id.have_no_members_layout)
    View haveNoMembersView;
    @BindView(R.id.not_found_user_search_layout)
    View notFoundUserSearchView;
    @BindView(R.id.found_user_layout_add_and_select)
    View foundUserAddAndSelectView;
    @BindView(R.id.members_list_view)
    View membersListView;
    @BindView(R.id.confirm_button)
    Button confirmButton;

    private SelectedMembersAdapter selectedMembersAdapter = new SelectedMembersAdapter();
    private MembersToBeSelectedAdapter membersToBeSelectedAdapter;

    public AddMembersToGroupFragmentDialog() {
        // Required empty public constructor
    }

    public static AddMembersToGroupFragmentDialog newInstance() {
        return new AddMembersToGroupFragmentDialog();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment]
        View view = inflater.inflate(R.layout.fragment_add_members_to_group, container, false);
        ButterKnife.bind(this, view);
        setBottomSheetBehavior();
        setSearchBehavior();
        return view;
    }

    private void setSearchBehavior() {
        deactivatedSearchEditText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                deactivatedSearchLayout.setVisibility(View.INVISIBLE);
                activatedSearchLayout.setVisibility(View.VISIBLE);
                activatedSearchEditText.requestFocus();
                //show keypad
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
            return false;
        });
    }

    @OnClick(R.id.search_back_button)
    public void onSearchBackButtonClicked() {
        deactivatedSearchLayout.setVisibility(View.VISIBLE);
        activatedSearchLayout.setVisibility(View.INVISIBLE);
//        userFoundLayout.setVisibility(View.GONE);
//        notFoundUserSearchLayout.setVisibility(View.GONE);
//        notSearchYetLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Member> memberList = new ArrayList<>();
        memberList.add(new Member("Hanan Sabry", "#425df"));
        memberList.add(new Member("Ahmed Sabry", "#022as"));
        memberList.add(new Member("Mohammed Sabry", "#525mm"));
        memberList.add(new Member("Ahmed Morsi", "#525mm"));
        memberList.add(new Member("Ahmed Morsi", "#525mm"));

        membersToBeSelectedAdapter = new MembersToBeSelectedAdapter(memberList, new OnSelectMemberToAddToGroupCallback() {
            @Override
            public void onSelectMember(Member member) {
                selectedMembersRecyclerView.setAdapter(selectedMembersAdapter);
                selectedMembersAdapter.selectMember(member);
                selectedMembersRecyclerView.smoothScrollToPosition(selectedMembersAdapter.getItemCount());
                if (selectedMembersAdapter.getItemCount() == 0) {
                    selectedMembersRecyclerView.setVisibility(View.GONE);
                    removeAllSelectedMembers.setVisibility(View.GONE);
                } else {
                    selectedMembersRecyclerView.setVisibility(View.VISIBLE);
                    removeAllSelectedMembers.setVisibility(View.VISIBLE);
                }
                membersSelectedCountText.setText(String.format(Locale.getDefault(), "%d Members selected", selectedMembersAdapter.getItemCount()));
            }

            @Override
            public void onRemoveSelection(Member member) {
                selectedMembersAdapter.unSelectMember(member);
                membersSelectedCountText.setText(String.format(Locale.getDefault(), "%d Members selected", selectedMembersAdapter.getItemCount()));
            }
        });
        membersRecyclerView.setAdapter(membersToBeSelectedAdapter);
    }

    @OnClick(R.id.remove_all_selected_members_button)
    public void onRemoveAllSelectedMembersClicked() {
        selectedMembersAdapter.removeAll();
        removeAllSelectedMembers.setVisibility(View.GONE);
        selectedMembersRecyclerView.setVisibility(View.GONE);
        membersSelectedCountText.setText("0 Members selected");
        membersToBeSelectedAdapter.notifyDataSetChanged();
    }

    @OnTextChanged(R.id.activated_search_edit_text)
    public void onSearchTextChanged(CharSequence s, int start, int count, int after) {
        if (s.toString().equals("")) {
//            haveNoMembersView.setVisibility(View.VISIBLE);
            membersListView.setVisibility(View.VISIBLE);
            notFoundUserSearchView.setVisibility(View.GONE);
            foundUserAddAndSelectView.setVisibility(View.GONE);
            confirmButton.setVisibility(View.GONE);
        } else if (s.toString().equals("am1234")) {
            //show the user
            haveNoMembersView.setVisibility(View.GONE);
            membersListView.setVisibility(View.GONE);
            notFoundUserSearchView.setVisibility(View.GONE);
            foundUserAddAndSelectView.setVisibility(View.VISIBLE);
            confirmButton.setVisibility(View.GONE);
        } else {
            //show no results found
            //filter list adapter
            haveNoMembersView.setVisibility(View.GONE);
            membersListView.setVisibility(View.GONE);
            notFoundUserSearchView.setVisibility(View.VISIBLE);
            foundUserAddAndSelectView.setVisibility(View.GONE);
        }
    }

    private void setBottomSheetBehavior() {
        BottomSheetBehavior behavior = ((BottomSheetDialog) getDialog()).getBehavior();
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        behavior.setDraggable(false);
    }
}