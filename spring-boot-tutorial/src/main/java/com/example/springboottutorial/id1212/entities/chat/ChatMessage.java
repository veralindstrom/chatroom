package com.example.springboottutorial.id1212.entities.chat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="message")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer messageId;
    private String message;
    private Date date;
    private Integer fileId;
    private Integer userId;
    private Integer chatroomId;

    public Integer getId() {
        return messageId;
    }

    public void setId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public Integer getfileId(){
        return fileId;
    }

    public void setfileId(Integer fileId){
        this.fileId = fileId;
    }

    public Integer getuserId(){
        return userId;
    }

    public void setuserId(Integer userId){
        this.userId = userId;
    }

    public Integer getchatroomId(){
        return chatroomId;
    }

    public void setchatroomId(Integer chatroomId){
        this.chatroomId = chatroomId;
    }
}
