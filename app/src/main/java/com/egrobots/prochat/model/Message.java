package com.egrobots.prochat.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Message implements Parcelable {

    private String text;
    private long time;
    private boolean sent;
    private boolean isRead;

    public Message() {
    }

    public Message(String text, long time, boolean sent, boolean isRead) {
        this.text = text;
        this.time = time;
        this.sent = sent;
        this.isRead = isRead;
    }


    protected Message(Parcel in) {
        text = in.readString();
        time = in.readLong();
        sent = in.readByte() != 0;
        isRead = in.readByte() != 0;
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFormattedTime() {
        DateFormat df = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
        return df.format(time);
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeLong(time);
        dest.writeByte((byte) (sent ? 1 : 0));
        dest.writeByte((byte) (isRead ? 1 : 0));
    }
}
