package com.example.springboottutorial.id1212.entities.chat;

import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface MessageRepository extends CrudRepository<Message, Integer> {
    Message findChatMessageByMessageId(Integer messageId);
    Message findChatMessageByUserId(Integer userId);
    Message findChatMessageByDate(Date date);
    Message findChatMessageByChatroomId(Integer chatroomId);
}
