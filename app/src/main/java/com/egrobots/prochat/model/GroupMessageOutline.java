package com.egrobots.prochat.model;

public class GroupMessageOutline {

    private String groupMessageTitle;
    private String groupMessageSubTitle;
    private String groupMessageDate;

    public GroupMessageOutline(String groupMessageTitle, String groupMessageSubTitle, String groupMessageDate) {
        this.groupMessageTitle = groupMessageTitle;
        this.groupMessageSubTitle = groupMessageSubTitle;
        this.groupMessageDate = groupMessageDate;
    }

    public String getGroupMessageTitle() {
        return groupMessageTitle;
    }

    public void setGroupMessageTitle(String groupMessageTitle) {
        this.groupMessageTitle = groupMessageTitle;
    }

    public String getGroupMessageSubTitle() {
        return groupMessageSubTitle;
    }

    public void setGroupMessageSubTitle(String groupMessageSubTitle) {
        this.groupMessageSubTitle = groupMessageSubTitle;
    }

    public String getGroupMessageDate() {
        return groupMessageDate;
    }

    public void setGroupMessageDate(String groupMessageDate) {
        this.groupMessageDate = groupMessageDate;
    }
}
