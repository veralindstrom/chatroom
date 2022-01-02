package com.example.springboottutorial.id1212.entities.bridges;

import org.springframework.data.repository.CrudRepository;

public interface ChatroomUserRepository extends CrudRepository<ChatroomUser, String> {
    ChatroomUser findChatroomUserByUser_idAndChatroom_id(Integer user_id, Integer chatroom_id);
    ChatroomUser findChatroomUserByAdmin(Boolean admin);
    ChatroomUser findChatroomUserByRole_id(Integer role_id);
    ChatroomUser findChatroomUserByFavorite(Boolean favorite);
}