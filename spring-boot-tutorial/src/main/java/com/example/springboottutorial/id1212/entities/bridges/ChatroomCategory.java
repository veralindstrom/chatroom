package com.example.springboottutorial.id1212.entities.bridges;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="chatroom_category")
public class ChatroomCategory implements Serializable {
    @Id
    private Integer chatroomId;

    private Integer categoryId;

    public void setcategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getcategoryId() {
        return categoryId;
    }

    public void setchatroomId(Integer chatroomId) {
        this.chatroomId = chatroomId;
    }

    public Integer getchatroomId() {
        return chatroomId;
    }
}
