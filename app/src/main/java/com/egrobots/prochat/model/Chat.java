package com.egrobots.prochat.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Chat implements Parcelable {

    private String chatId;
    private String chatFrom;
    private String chatTo;
    private List<Message> messages;

    public Chat() {
    }

    protected Chat(Parcel in) {
        chatId = in.readString();
        chatFrom = in.readString();
        chatTo = in.readString();
        messages = in.createTypedArrayList(Message.CREATOR);
    }

    public static final Creator<Chat> CREATOR = new Creator<Chat>() {
        @Override
        public Chat createFromParcel(Parcel in) {
            return new Chat(in);
        }

        @Override
        public Chat[] newArray(int size) {
            return new Chat[size];
        }
    };

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getChatFrom() {
        return chatFrom;
    }

    public void setChatFrom(String chatFrom) {
        this.chatFrom = chatFrom;
    }

    public String getChatTo() {
        return chatTo;
    }

    public void setChatTo(String chatTo) {
        this.chatTo = chatTo;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(chatId);
        dest.writeString(chatFrom);
        dest.writeString(chatTo);
        dest.writeTypedList(messages);
    }
}
