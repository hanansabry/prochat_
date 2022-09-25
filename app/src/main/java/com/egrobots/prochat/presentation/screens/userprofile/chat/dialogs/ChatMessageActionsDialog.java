package com.egrobots.prochat.presentation.screens.userprofile.chat.dialogs;

import android.content.Context;

import com.egrobots.prochat.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatMessageActionsDialog extends BottomSheetDialog {

    public ChatMessageActionsDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.chat_message_actions_dialog);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.delete_msg_button)
    public void onDeleteMessageClicked() {
        DeleteMessageDialog deleteMessageDialog = new DeleteMessageDialog(getContext());
        deleteMessageDialog.show();
    }

    @OnClick(R.id.reply_to_msg_button)
    public void onReplyClicked() {
        ReplyChatMessageDialog replyDialog = new ReplyChatMessageDialog(getContext());
        replyDialog.show();
    }
}
