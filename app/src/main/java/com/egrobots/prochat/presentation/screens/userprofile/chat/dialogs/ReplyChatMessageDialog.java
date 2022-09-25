package com.egrobots.prochat.presentation.screens.userprofile.chat.dialogs;

import android.content.Context;

import com.egrobots.prochat.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;

public class ReplyChatMessageDialog extends BottomSheetDialog {
    public ReplyChatMessageDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.reply_message_dialog);
    }

//    public static ReplyChatMessageDialog newInstance() {
//        ReplyChatMessageDialog fragment = new ReplyChatMessageDialog();
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public void setupDialog(Dialog dialog, int style) {
//        View contentView = View.inflate(getContext(), R.layout.reply_message_dialog, null);
//        dialog.setContentView(contentView);
//        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
//    }
}
