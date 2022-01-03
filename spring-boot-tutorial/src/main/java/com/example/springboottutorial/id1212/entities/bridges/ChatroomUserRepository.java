package com.example.springboottutorial.id1212.entities.bridges;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface ChatroomUserRepository extends CrudRepository<ChatroomUser, Integer> {
    ArrayList<ChatroomUser> findChatroomUserByUserId(Integer userId);
    ChatroomUser findChatroomUserByUserIdAndChatroomId(Integer userId, Integer chatroomId);
    //ChatroomUser findChatroomUserByAdmin(Boolean admin);
    ChatroomUser findChatroomUserByRoleId(Integer roleId);
    //ChatroomUser findChatroomUserByFavorite(Boolean favorite);

    @Query("SELECT c.chatroomId FROM ChatroomUser c WHERE c.userId = ?1")
    List<Integer> getAllChatroomIdsByUserId(Integer userId);
}