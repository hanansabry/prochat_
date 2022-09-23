package com.egrobots.prochat.presentation.dialogs.authentication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egrobots.prochat.R;
import com.egrobots.prochat.di.DaggerBottomSheetDialogFragment;
import com.egrobots.prochat.utils.Constants;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerifyAccountBottomSheetDialog extends DaggerBottomSheetDialogFragment {

    public static final String TAG = "VerifyAccountBottomSheetDialog";
    private String phoneNumber;
    private String userName;
    private String password;

    public static VerifyAccountBottomSheetDialog newInstance(String phoneNumber, String userName, String password) {
        VerifyAccountBottomSheetDialog fragmentDialog = new VerifyAccountBottomSheetDialog();
        Bundle arguments = new Bundle();
        arguments.putString(Constants.PHONE, phoneNumber);
        arguments.putString(Constants.USER_NAME, userName);
        arguments.putString(Constants.PASSWORD, password);
        fragmentDialog.setArguments(arguments);
        return fragmentDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.verify_account_dialog, container, false);
        ButterKnife.bind(this, view);
        if (getArguments() != null) {
            phoneNumber = getArguments().getString(Constants.PHONE);
            userName = getArguments().getString(Constants.USER_NAME);
            password = getArguments().getString(Constants.PASSWORD);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
