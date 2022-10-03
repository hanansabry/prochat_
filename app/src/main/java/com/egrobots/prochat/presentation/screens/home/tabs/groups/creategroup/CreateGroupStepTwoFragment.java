package com.egrobots.prochat.presentation.screens.home.tabs.groups.creategroup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.egrobots.prochat.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateGroupStepTwoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateGroupStepTwoFragment extends Fragment {

    public static final String TAG = "CreateGroupStepTwoFragment";

    @BindView(R.id.close_messages_switch)
    Switch closeMessagesSwitch;
    @BindView(R.id.week_days_layout)
    View weekDaysLayout;
    @BindView(R.id.group_closed_layout)
    View groupClosedLayout;

    public CreateGroupStepTwoFragment() {
        // Required empty public constructor
    }

    public static CreateGroupStepTwoFragment newInstance() {
        return new CreateGroupStepTwoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_group_step_two, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        closeMessagesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    groupClosedLayout.setVisibility(View.VISIBLE);
                    weekDaysLayout.setVisibility(View.INVISIBLE);
                } else {
                    groupClosedLayout.setVisibility(View.INVISIBLE);
                    weekDaysLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}