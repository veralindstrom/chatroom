package com.example.springboottutorial.id1212.entities.chat;

import org.springframework.data.repository.CrudRepository;

public interface ChatroomRepository extends CrudRepository<Chatroom, String> {
    Chatroom findChatRoomByChatroom_id(Integer id);
    Chatroom findChatRoomByName(String name);
}

