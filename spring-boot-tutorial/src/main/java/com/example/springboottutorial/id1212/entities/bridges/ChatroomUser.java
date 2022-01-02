package com.example.springboottutorial.id1212.entities.bridges;

import javax.persistence.*;

@Entity
@Table(name="chatroom_user")
public class ChatroomUser {
    @Id
    private Integer chatroomId;
    private Integer userId;
    private Integer roleId;
    private boolean admin;
    private boolean favorite;

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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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
