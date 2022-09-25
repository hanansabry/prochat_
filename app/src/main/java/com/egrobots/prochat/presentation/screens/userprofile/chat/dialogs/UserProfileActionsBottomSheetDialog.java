package com.egrobots.prochat.presentation.screens.userprofile.chat.dialogs;

import android.content.Context;

import com.egrobots.prochat.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;

public class UserProfileActionsBottomSheetDialog extends BottomSheetDialog {

    public UserProfileActionsBottomSheetDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.user_profile_actions_menu_bottom_sheet_dialog);
    }
}
