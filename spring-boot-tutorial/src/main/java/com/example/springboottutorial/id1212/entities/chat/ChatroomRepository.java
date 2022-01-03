package com.example.springboottutorial.id1212.entities.chat;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatroomRepository extends CrudRepository<Chatroom, String> {
    Chatroom findChatRoomByChatroomId(Integer chatroomId);
    Chatroom findChatRoomByName(String name);

    @Query("select c.chatroomId from Chatroom c")
    List<Integer> getAllId();
}

