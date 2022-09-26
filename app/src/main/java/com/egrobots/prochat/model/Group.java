package com.egrobots.prochat.model;

import java.util.List;

public class Group {

    private String groupId;
    private String groupName;
    private String groupType;
    private boolean isPrivate;
    private List<Chat> groupChats;

    public Group() {
    }

    public Group(String groupName, String groupType, boolean isPrivate) {
        this.groupName = groupName;
        this.groupType = groupType;
        this.isPrivate = isPrivate;
    }

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

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public List<Chat> getGroupChats() {
        return groupChats;
    }

    public void setGroupChats(List<Chat> groupChats) {
        this.groupChats = groupChats;
    }
}
