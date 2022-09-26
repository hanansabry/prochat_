package com.egrobots.prochat.callbacks;

import com.egrobots.prochat.model.Chat;
import com.egrobots.prochat.model.GroupChatOutline;

public interface OnGroupChatSelectedCallback {
    void onGroupChatSelected(Chat chat);
}