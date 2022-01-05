package com.example.springboottutorial.id1212.entities.bridges.ChatroomUser;

import com.example.springboottutorial.id1212.entities.bridges.ChatroomUser.ChatroomUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ChatroomUserRepository extends CrudRepository<ChatroomUser, Integer> {
    ArrayList<ChatroomUser> findChatroomUsersByUserId(Integer userId);
    ArrayList<ChatroomUser> findChatroomUsersByChatroomId(Integer chatroomId);
    ChatroomUser findChatroomUserByUserIdAndChatroomId(Integer userId, Integer chatroomId);
    //ChatroomUser findChatroomUserByAdmin(Boolean admin);
    ChatroomUser findChatroomUserByRoleId(Integer roleId);
    //ChatroomUser findChatroomUserByFavorite(Boolean favorite);

    @Query("SELECT c.chatroomId FROM ChatroomUser c WHERE c.userId = ?1")
    ArrayList<Integer> getAllChatroomIdsByUserId(Integer userId);

    @Query("SELECT c.userId FROM ChatroomUser c WHERE c.chatroomId = ?1")
    ArrayList<Integer> getAllUserIdsByChatroomId(Integer userId);
}