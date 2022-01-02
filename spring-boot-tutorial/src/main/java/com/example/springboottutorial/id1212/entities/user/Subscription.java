package com.example.springboottutorial.id1212.entities.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer sub_id;
    private Integer user_id;
    private Integer chatroom_id;
    private Integer category_id;
    private String mail_message;

    public Integer getSub_id() { return sub_id; }

    public void setSub_id(Integer sub_id) {
        this.sub_id = sub_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getChatroom_id() {
        return chatroom_id;
    }

    public void setChatroom_id(Integer chatroom_id) {
        this.chatroom_id = chatroom_id;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public String getMail_message() {
        return mail_message;
    }

    public void setMail_message(String mail_message) {
        this.mail_message = mail_message;
    }
}
