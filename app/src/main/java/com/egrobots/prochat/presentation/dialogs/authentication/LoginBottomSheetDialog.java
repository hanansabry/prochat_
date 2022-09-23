package com.egrobots.prochat.presentation.dialogs.authentication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egrobots.prochat.R;
import com.egrobots.prochat.di.DaggerBottomSheetDialogFragment;
import com.egrobots.prochat.di.ViewModelProviderFactory;
import com.egrobots.prochat.viewmodels.AuthenticationViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginBottomSheetDialog extends DaggerBottomSheetDialogFragment {

    public static final String TAG = "LoginBottomSheetDialog";

    @Inject
    ViewModelProviderFactory providerFactory;

    public static LoginBottomSheetDialog newInstance() {
        return new LoginBottomSheetDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_bottom_dialog, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AuthenticationViewModel authenticationViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(AuthenticationViewModel.class);
    }
//    public LoginBottomSheetDialog(@NonNull Context context) {
//        super(context);
//        setContentView(R.layout.login_bottom_dialog);
//        ButterKnife.bind(this);
//    }
//
//    @OnClick(R.id.sign_up_textview)
//    public void onSignUpClicked() {
//        dismiss();
////        new SignUpBottomSheetDialog(getContext()).show();
//    }
//
//    @OnClick(R.id.close_dialog_button)
//    public void onCloseDialogClicked() {
//        dismiss();
//    }
}
