package com.example.springboottutorial.id1212.entities.chat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="message")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer message_id;
    private String message;
    private Date date;
    private Integer file_id;
    private Integer user_id;
    private Integer chatroom_id;

    public Integer getId() {
        return message_id;
    }

    public void setId(Integer message_id) {
        this.message_id = message_id;
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

    public Integer getFile_id(){
        return file_id;
    }

    public void setFile_id(Integer file_id){
        this.file_id = file_id;
    }

    public Integer getUser_id(){
        return user_id;
    }

    public void setUser_id(Integer user_id){
        this.user_id = user_id;
    }

    public Integer getChatroom_id(){
        return chatroom_id;
    }

    public void setChatroom_id(Integer chatroom_id){
        this.chatroom_id = chatroom_id;
    }
}
