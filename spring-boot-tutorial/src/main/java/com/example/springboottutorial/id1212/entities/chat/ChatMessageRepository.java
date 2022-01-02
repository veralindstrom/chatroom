package com.example.springboottutorial.id1212.entities.chat;

import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface ChatMessageRepository extends CrudRepository<ChatMessage, Integer> {
    ChatMessage findChatMessageByMessageId(Integer messageId);
    ChatMessage findChatMessageByUserId(Integer userId);
    ChatMessage findChatMessageByDate(Date date);
    ChatMessage findChatMessageByChatroomId(Integer chatroomId);
}
