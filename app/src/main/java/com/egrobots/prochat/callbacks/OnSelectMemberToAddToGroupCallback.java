package com.egrobots.prochat.callbacks;

import com.egrobots.prochat.model.Member;

public interface OnSelectMemberToAddToGroupCallback {

    void onSelectMember(Member member);

    void onRemoveSelection(Member member);

    void onNotFoundMember();

    void onFoundMembers();
}
