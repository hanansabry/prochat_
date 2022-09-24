package com.egrobots.prochat.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ChatMessage {

    private String text;
    private long time;
    private boolean sent;

    public ChatMessage(String text, long time, boolean sent) {
        this.text = text;
        this.time = time;
        this.sent = sent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        DateFormat df = new SimpleDateFormat("HH:mm aa", Locale.getDefault());
        String formatted = df.format(df);
        return formatted;
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
}
