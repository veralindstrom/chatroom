package com.example.id1212.ChatroomApp.entities.bridges.ChatroomCategory;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="chatroom_category")
@IdClass(ChatroomCategoryId.class)
public class ChatroomCategory implements Serializable {
    @Id
    private Integer chatroomId;
    @Id
    private Integer categoryId;

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setChatroomId(Integer chatroomId) {
        this.chatroomId = chatroomId;
    }

    public Integer getChatroomId() {
        return chatroomId;
    }

}
