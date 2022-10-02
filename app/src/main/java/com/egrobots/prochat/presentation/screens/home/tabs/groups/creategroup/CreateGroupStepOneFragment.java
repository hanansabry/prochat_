package com.egrobots.prochat.presentation.screens.home.tabs.groups.creategroup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.egrobots.prochat.R;

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
    private boolean isPublicGroupSelected;
    private boolean isPrivateGroupSelected;

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
            publicGroupButton.setImageDrawable(getContext().getDrawable(R.drawable.public_group_selected));
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
            privateGroupButton.setImageDrawable(getContext().getDrawable(R.drawable.private_group_selected));
            addMembersLayout.setVisibility(View.VISIBLE);
        }
    }
}