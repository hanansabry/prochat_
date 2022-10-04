package com.egrobots.prochat.presentation.screens.home.tabs.groups.creategroup;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egrobots.prochat.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateGroupStepThreeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateGroupStepThreeFragment extends Fragment {

    @BindView(R.id.all_days_button)
    MaterialButton allDaysButton;
    @BindView(R.id.daily_button)
    MaterialButton dailyButton;
    @BindView(R.id.all_days_times_layout)
    View allDaysLayout;
    @BindView(R.id.all_days_select_times_view)
    View allDaysSelectTimesView;
    @BindView(R.id.daily_layout)
    View dailyLayout;

    public CreateGroupStepThreeFragment() {
        // Required empty public constructor
    }

    public static CreateGroupStepThreeFragment newInstance() {
        return new CreateGroupStepThreeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_group_step_three, container, false);
        ButterKnife.bind(this, view);
        setAllDaysSelectTimesClickListeners();
        return view;
    }

    @OnClick(R.id.all_days_button)
    public void onAllDaysSelected() {
        //selected
        allDaysButton.setBackgroundTintList(ColorStateList.valueOf(getContext().getColor(R.color.GreenyShadeOne)));
        allDaysButton.setIcon(getContext().getDrawable(R.drawable.radio_button_checked_green));
        allDaysLayout.setVisibility(View.VISIBLE);
        //not selected
        dailyButton.setBackgroundTintList(ColorStateList.valueOf(getContext().getColor(R.color.SoftGray)));
        dailyButton.setIcon(getContext().getDrawable(R.drawable.radio_button_unchecked_black_24dp_1));
        dailyLayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.daily_button)
    public void onDailySelected() {
        //not selected
        allDaysButton.setBackgroundTintList(ColorStateList.valueOf(getContext().getColor(R.color.SoftGray)));
        allDaysButton.setIcon(getContext().getDrawable(R.drawable.radio_button_unchecked_black_24dp_1));
        allDaysLayout.setVisibility(View.GONE);
        //selected
        dailyButton.setBackgroundTintList(ColorStateList.valueOf(getContext().getColor(R.color.GreenyShadeOne)));
        dailyButton.setIcon(getContext().getDrawable(R.drawable.radio_button_checked_green));
        dailyLayout.setVisibility(View.VISIBLE);
    }

    private void setAllDaysSelectTimesClickListeners() {
        View fromTime = allDaysSelectTimesView.findViewById(R.id.from_time_layout);
        View toTime = allDaysSelectTimesView.findViewById(R.id.to_time_layout);
        fromTime.setOnClickListener(v -> {
            MaterialTimePicker timePicker= new MaterialTimePicker.Builder()
                    // set the title for the alert dialog
                    .setTitleText("Add Start Time")
                    .setHour(12)
                    .setMinute(10)
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .build();

            timePicker.show(getChildFragmentManager(), "ALL_DAYS_FROM_TIME");
        });
        toTime.setOnClickListener(v -> {
            MaterialTimePicker timePicker= new MaterialTimePicker.Builder()
                    // set the title for the alert dialog
                    .setTitleText("Add Start Time")
                    .setHour(12)
                    .setMinute(10)
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .build();

            timePicker.show(getChildFragmentManager(), "ALL_DAYS_FROM_TIME");
        });
    }
}