package com.example.buy.entity;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class Message extends LitePalSupport implements Comparable<Message>{

    @Column(unique = true, defaultValue = "0")
    private int id;
    /**
     * Timestamp when the message was sent
     */
    private long time;
    private int sendUserId;
    private int receiveUserId;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(int sendUserId) {
        this.sendUserId = sendUserId;
    }

    public int getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(int receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int compareTo(Message message) {
        return (int) (time - message.time);
    }
}
