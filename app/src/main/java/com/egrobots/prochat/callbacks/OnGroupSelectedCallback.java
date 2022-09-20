package com.egrobots.prochat.callbacks;

import com.egrobots.prochat.model.GroupMessageOutline;

public interface OnGroupSelectedCallback {
    void onGroupSelected(GroupMessageOutline groupMessageOutline);
}