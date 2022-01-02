package com.example.springboottutorial.id1212.entities.bridges;

import javax.persistence.*;

@Entity
@Table(name="chatroom_user")
public class ChatroomUser {
    @Id
    private Integer chatroom_id;
    private Integer user_id;
    private Integer role_id;
    private boolean admin;
    private boolean favorite;

    public Integer getChatroom_id() {
        return chatroom_id;
    }

    public void setChatroom_id(Integer chatroom_id) {
        this.chatroom_id = chatroom_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public boolean getAdmin(){
        return admin;
    }

    public void setStatus(boolean admin){
        this.admin = admin;
    }

    public boolean getFavorite(){
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
