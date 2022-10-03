package com.egrobots.prochat.presentation.screens.home.tabs.groups.creategroup;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Toast;

import com.egrobots.prochat.R;
import com.egrobots.prochat.callbacks.AddingGroupListener;
import com.egrobots.prochat.presentation.screens.userprofile.chat.UserChatFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateNewGroupMainDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateNewGroupMainDialog extends BottomSheetDialogFragment implements AddingGroupListener {

    public static final String TAG = "CreateNewGroupMainDialog";

    private boolean isFirstStepCompleted;
    private boolean isSecondStepCompleted;
    private boolean isThirdStepCompleted;

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
    @BindView(R.id.back_button)
    ImageButton backButton;

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
        setBottomSheetBehavior();

        FragmentTransaction fragmentTransaction
                = getChildFragmentManager().beginTransaction()
                .add(R.id.step_fragment, CreateGroupStepOneFragment.newInstance());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        return view;
    }

    private void setBottomSheetBehavior() {
        BottomSheetBehavior behavior = ((BottomSheetDialog)getDialog()).getBehavior();
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        behavior.setDraggable(false);
        behavior.setPeekHeight(800);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

    @OnClick(R.id.next_button)
    public void onActionButtonClicked() {
        if (isFirstStepCompleted) {
            goToStepTwo();
        }
    }

    @OnClick(R.id.back_button)
    public void onBackButtonClicked() {
//        CreateGroupStepTwoFragment stepTwo
//                = (CreateGroupStepTwoFragment) getChildFragmentManager().findFragmentByTag(CreateGroupStepTwoFragment.TAG);
//        if (stepTwo != null && stepTwo.isVisible()) {
//            backToStepOne();
//        }
        getChildFragmentManager().popBackStack();
        backToStepOne();
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
        nextButton.setEnabled(false);
        nextButton.setBackgroundResource(R.drawable.dimmed_button_bg);
        backButton.setVisibility(View.VISIBLE);
    }

    private void backToStepOne() {
        firstStepIcon.setImageDrawable(getContext().getDrawable(R.drawable.info_icon_white));
        firstStepBg.setBackgroundTintList(ColorStateList.valueOf(getContext().getColor(R.color.colorPrimary)));
        secondStepBg.setBackgroundTintList(ColorStateList.valueOf(getContext().getColor(R.color.LightBlue)));
        nextButton.setEnabled(true);
        nextButton.setBackgroundResource(R.drawable.active_button_bg);
        backButton.setVisibility(View.GONE);
    }
}