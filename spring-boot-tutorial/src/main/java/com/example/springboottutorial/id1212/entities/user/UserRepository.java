package com.example.springboottutorial.id1212.entities.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    User findUserByEmailAndPassword(String email,String password);

}
