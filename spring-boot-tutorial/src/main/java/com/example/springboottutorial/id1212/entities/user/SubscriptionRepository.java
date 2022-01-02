package com.example.springboottutorial.id1212.entities.user;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface SubscriptionRepository extends CrudRepository<Subscription, String> {
    Subscription findSubscriptionChatroom_id(Integer chatroom_id);
    ArrayList<Subscription> findSubscriptionsByUser_id(Integer user_id);
}