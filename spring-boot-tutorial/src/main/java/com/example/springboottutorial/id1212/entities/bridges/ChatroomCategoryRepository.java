package com.example.springboottutorial.id1212.entities.bridges;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ChatroomCategoryRepository extends CrudRepository<ChatroomCategory, String> {
    ChatroomCategory findChatroomCategoryByCategory_idAndChatroom_id(Integer category_id, Integer chatroom_id);
    ArrayList<ChatroomCategory> findChatroomCategoriesByCategory_id(Integer category_id);
}