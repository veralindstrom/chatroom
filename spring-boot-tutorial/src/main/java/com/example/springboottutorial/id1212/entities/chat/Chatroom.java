package com.example.springboottutorial.id1212.entities.chat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Chatroom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer chatroom_id;
    private String name;
    private Integer user_count;
    private boolean status;

    public Integer getId() {
        return chatroom_id;
    }

    public void setId(Integer chatroom_id) {
        this.chatroom_id = chatroom_id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Integer getUserCount(){
        return user_count;
    }

    public void addUserCount(Integer user_count){
        this.user_count += user_count;
    }

    public boolean getStatus(){
        return status;
    }

    public void setStatus(boolean status){
        this.status = status;
    }

}
