package com.example.springboottutorial.id1212.entities.bridges;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="chatroom_category")
public class ChatroomCategory {
    @Id
    private Integer chatroom_id;
    @Id
    private Integer category_id;

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setChatroom_id(Integer chatroom_id) {
        this.chatroom_id = chatroom_id;
    }

    public Integer getChatroom_id() {
        return chatroom_id;
    }
}
