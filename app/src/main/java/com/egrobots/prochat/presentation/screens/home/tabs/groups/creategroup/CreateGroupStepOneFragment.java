package com.egrobots.prochat.presentation.screens.home.tabs.groups.creategroup;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.egrobots.prochat.R;
import com.egrobots.prochat.callbacks.AddingGroupListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateGroupStepOneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateGroupStepOneFragment extends Fragment {

    @BindView(R.id.add_members_layout)
    View addMembersLayout;
    @BindView(R.id.public_group_icon)
    ImageButton publicGroupButton;
    @BindView(R.id.private_group_icon)
    ImageButton privateGroupButton;
    @BindView(R.id.group_name_edit_text)
    EditText groupNameEditText;
    private boolean isPublicGroupSelected;
    private boolean isPrivateGroupSelected;
    private AddingGroupListener addingGroupListener;

    public CreateGroupStepOneFragment() {
        // Required empty public constructor
    }
    public static CreateGroupStepOneFragment newInstance() {
        return new CreateGroupStepOneFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.group_main_info_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.public_group_icon)
    public void onPublicGroupClicked() {
        if (isPublicGroupSelected) {
            isPublicGroupSelected = false;
            publicGroupButton.setImageDrawable(getContext().getDrawable(R.drawable.public_group));
        } else {
            isPublicGroupSelected = true;
            isPrivateGroupSelected = false;
            privateGroupButton.setImageDrawable(getContext().getDrawable(R.drawable.private_group));
            publicGroupButton.setImageDrawable(getContext().getDrawable(R.drawable.public_group_selected));
        }
        if (isPublicGroupSelected && !groupNameEditText.getText().toString().isEmpty()) {
            setFirstStepCompleted(true);
        } else {
            setFirstStepCompleted(false);
        }
        addMembersLayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.private_group_icon)
    public void onPrivateGroupClicked() {
        if (isPrivateGroupSelected) {
            isPrivateGroupSelected = false;
            privateGroupButton.setImageDrawable(getContext().getDrawable(R.drawable.private_group));
            addMembersLayout.setVisibility(View.GONE);
        } else {
            isPrivateGroupSelected = true;
            isPublicGroupSelected = false;
            publicGroupButton.setImageDrawable(getContext().getDrawable(R.drawable.public_group));
            privateGroupButton.setImageDrawable(getContext().getDrawable(R.drawable.private_group_selected));
            addMembersLayout.setVisibility(View.VISIBLE);
        }

        if (isPrivateGroupSelected && !groupNameEditText.getText().toString().isEmpty()) {
            setFirstStepCompleted(true);
        } else {
            setFirstStepCompleted(false);
        }
    }

    @OnTextChanged(R.id.group_name_edit_text)
    public void onTextChanged(CharSequence s, int start, int count, int after) {
        if (!s.toString().isEmpty() && (isPublicGroupSelected || isPrivateGroupSelected)) {
            //enable actions button in main layout
            setFirstStepCompleted(true);
        } else {
            setFirstStepCompleted(false);
        }
    }

    private void setFirstStepCompleted(boolean isCompleted) {
        ((CreateNewGroupMainDialog)getParentFragment()).isFirstStepCompleted(isCompleted);
    }
}