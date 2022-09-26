package com.egrobots.prochat.callbacks;

import com.egrobots.prochat.model.Chat;
import com.egrobots.prochat.model.Group;

public interface OnGroupSelectedCallback {
    void onGroupSelected(Group group);
}