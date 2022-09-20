package com.egrobots.prochat.model;

public class ChatMessage {

    private String text;
    private String time;
    private boolean sent;

    public ChatMessage(String text, String time, boolean sent) {
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
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }
}
