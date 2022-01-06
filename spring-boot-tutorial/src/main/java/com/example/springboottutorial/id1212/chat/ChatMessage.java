package com.example.springboottutorial.id1212.chat;


import java.util.Date;

public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
    private String date;
    private Integer chatroomId;
    private Integer userId;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(Integer chatroomId) {
        this.chatroomId = chatroomId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}