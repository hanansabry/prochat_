package com.egrobots.prochat.presentation.screens.home.tabs.groups.creategroup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egrobots.prochat.R;
import com.egrobots.prochat.presentation.screens.home.tabs.ChatsFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateNewGroupMainDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateNewGroupMainDialog extends BottomSheetDialogFragment {

    public static final String TAG = "CreateNewGroupMainDialog";

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
        BottomSheetBehavior behavior = ((BottomSheetDialog)getDialog()).getBehavior();
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        behavior.setDraggable(false);
        behavior.setPeekHeight(800);

        FragmentTransaction fragmentTransaction
                = getChildFragmentManager().beginTransaction()
                .replace(R.id.step_fragment, CreateGroupStepOneFragment.newInstance());
        fragmentTransaction.commit();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}