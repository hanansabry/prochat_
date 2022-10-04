package com.egrobots.prochat.presentation.screens.home.tabs.groups.creategroup;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.egrobots.prochat.R;
import com.egrobots.prochat.callbacks.AddingGroupListener;
import com.egrobots.prochat.utils.Constants;
import com.egrobots.prochat.utils.CustomBottomSheetFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateNewGroupMainDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateNewGroupMainDialog extends CustomBottomSheetFragment implements AddingGroupListener {

    public static final String TAG = "CreateNewGroupMainDialog";

    private boolean isFirstStepCompleted;
    private boolean isSecondStepCompleted = true;
    private boolean isThirdStepCompleted = true;
    private int currentStep = 1;

    @BindView(R.id.next_button)
    Button nextButton;
    @BindView(R.id.first_step_icon)
    ImageView firstStepIcon;
    @BindView(R.id.first_step_bg)
    View firstStepBg;
    @BindView(R.id.second_step_icon)
    ImageView secondStepIcon;
    @BindView(R.id.second_step_bg)
    View secondStepBg;
    @BindView(R.id.third_step_icon)
    ImageView thirdStepIcon;
    @BindView(R.id.third_step_bg)
    View thirdStepBg;
    @BindView(R.id.back_button)
    ImageButton backButton;
    @BindView(R.id.main_layout)
    ConstraintLayout mainLayout;

    public CreateNewGroupMainDialog() {
        // Required empty public constructor
    }

    public static CreateNewGroupMainDialog newInstance() {
        return new CreateNewGroupMainDialog();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.create_new_group_main_layout, container, false);
        ButterKnife.bind(this, view);
        setMainLayout(mainLayout, Constants.DIALOG_HEIGHT_PERCENT);
        FragmentTransaction fragmentTransaction
                = getChildFragmentManager().beginTransaction()
                .add(R.id.step_fragment, CreateGroupStepOneFragment.newInstance());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        return view;
    }

    @Override
    public void isFirstStepCompleted(boolean isCompleted) {
        if (isCompleted) {
            nextButton.setEnabled(true);
            nextButton.setBackgroundResource(R.drawable.active_button_bg);
            isFirstStepCompleted = true;
        } else {
            nextButton.setEnabled(false);
            nextButton.setBackgroundResource(R.drawable.dimmed_button_bg);
            isFirstStepCompleted = false;
        }
    }

    @Override
    public void isSecondStepCompleted(boolean isCompleted) {
        if (isCompleted) {
            nextButton.setEnabled(true);
            nextButton.setBackgroundResource(R.drawable.active_button_bg);
            isSecondStepCompleted = true;
        } else {
            nextButton.setEnabled(false);
            nextButton.setBackgroundResource(R.drawable.dimmed_button_bg);
            isSecondStepCompleted = false;
        }
    }

    @Override
    public void isThirdStepCompleted(boolean isCompleted) {
        if (isCompleted) {
            nextButton.setEnabled(true);
            nextButton.setBackgroundResource(R.drawable.active_button_bg);
            isThirdStepCompleted = true;
        } else {
            nextButton.setEnabled(false);
            nextButton.setBackgroundResource(R.drawable.dimmed_button_bg);
            isThirdStepCompleted = false;
        }
    }

    @OnClick(R.id.next_button)
    public void onActionButtonClicked() {
        if (isFirstStepCompleted && currentStep == 1) {
            goToStepTwo();
            currentStep++;
        } else if (isSecondStepCompleted && currentStep == 2) {
            goToStepThree();
            currentStep++;
        } else if (isThirdStepCompleted && currentStep == 3) {
            currentStep = -1;
        } else if (currentStep == -1) {
            //finish
        }
    }

    @OnClick(R.id.back_button)
    public void onBackButtonClicked() {
        if (currentStep == 2) {
            backToStepOne();
        } else if (currentStep == 3) {
            backToStepTwo();
        }
    }

    private void goToStepTwo() {
        firstStepIcon.setImageDrawable(getContext().getDrawable(R.drawable.check_icon));
        firstStepBg.setBackgroundTintList(ColorStateList.valueOf(getContext().getColor(R.color.colorSecondary)));
        secondStepBg.setBackgroundTintList(ColorStateList.valueOf(getContext().getColor(R.color.colorPrimary)));

        FragmentTransaction fragmentTransaction
                = getChildFragmentManager().beginTransaction()
                .add(R.id.step_fragment, CreateGroupStepTwoFragment.newInstance());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
//        nextButton.setEnabled(false);
//        nextButton.setBackgroundResource(R.drawable.dimmed_button_bg);
        backButton.setVisibility(View.VISIBLE);
    }

    private void goToStepThree() {
        secondStepIcon.setImageDrawable(getContext().getDrawable(R.drawable.check_icon));
        secondStepBg.setBackgroundTintList(ColorStateList.valueOf(getContext().getColor(R.color.colorSecondary)));
        thirdStepBg.setBackgroundTintList(ColorStateList.valueOf(getContext().getColor(R.color.colorPrimary)));

        FragmentTransaction fragmentTransaction
                = getChildFragmentManager().beginTransaction()
                .add(R.id.step_fragment, CreateGroupStepThreeFragment.newInstance());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
//        nextButton.setEnabled(false);
//        nextButton.setBackgroundResource(R.drawable.dimmed_button_bg);
        nextButton.setText("Create");
        backButton.setVisibility(View.VISIBLE);
    }

    private void backToStepOne() {
        currentStep--;
        getChildFragmentManager().popBackStack();
        firstStepIcon.setImageDrawable(getContext().getDrawable(R.drawable.info_icon_white));
        firstStepBg.setBackgroundTintList(ColorStateList.valueOf(getContext().getColor(R.color.colorPrimary)));
        secondStepBg.setBackgroundTintList(ColorStateList.valueOf(getContext().getColor(R.color.LightBlue)));
        nextButton.setEnabled(true);
        nextButton.setBackgroundResource(R.drawable.active_button_bg);
        nextButton.setText("Next");
        backButton.setVisibility(View.GONE);
    }

    private void backToStepTwo() {
        currentStep--;
        getChildFragmentManager().popBackStack();
        secondStepIcon.setImageDrawable(getContext().getDrawable(R.drawable.calendar_icon));
        secondStepBg.setBackgroundTintList(ColorStateList.valueOf(getContext().getColor(R.color.colorPrimary)));
        thirdStepBg.setBackgroundTintList(ColorStateList.valueOf(getContext().getColor(R.color.LightBlue)));
        nextButton.setEnabled(true);
        nextButton.setText("Next");
        nextButton.setBackgroundResource(R.drawable.active_button_bg);
    }
}