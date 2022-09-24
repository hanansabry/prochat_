package com.egrobots.prochat.model;

import java.util.List;

public class Group {

    private String groupId;
    private String groupName;
    private String groupType;
    private List<String> groupChats;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public List<String> getGroupChats() {
        return groupChats;
    }

    public void setGroupChats(List<String> groupChats) {
        this.groupChats = groupChats;
    }
}
