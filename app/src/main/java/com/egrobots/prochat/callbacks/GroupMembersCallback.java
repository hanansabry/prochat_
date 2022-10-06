package com.egrobots.prochat.callbacks;

import com.egrobots.prochat.model.Member;

public interface GroupMembersCallback {

    void onMemberLongClicked(Member member);
    void onMemberClicked(Member member);
}
