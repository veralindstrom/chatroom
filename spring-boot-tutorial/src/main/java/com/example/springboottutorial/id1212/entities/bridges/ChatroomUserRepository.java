package com.example.springboottutorial.id1212.entities.bridges;

import org.springframework.data.repository.CrudRepository;

public interface ChatroomUserRepository extends CrudRepository<ChatroomUser, Integer> {
    ChatroomUser findChatroomUserByUserIdAndChatroomId(Integer userId, Integer chatroomId);
    //ChatroomUser findChatroomUserByAdmin(Boolean admin);
    ChatroomUser findChatroomUserByRoleId(Integer roleId);
    //ChatroomUser findChatroomUserByFavorite(Boolean favorite);
}