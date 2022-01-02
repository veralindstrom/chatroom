package com.example.springboottutorial.id1212.entities.chat;

import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface ChatMessageRepository extends CrudRepository<ChatMessage, String> {
    ChatMessage findChatMessageByMessage_id(Integer id);
    ChatMessage findChatMessageByUser_id(Integer id);
    ChatMessage findChatMessageByDate(Date date);
    ChatMessage findChatMessageByChatroom_id(Integer id);
}
