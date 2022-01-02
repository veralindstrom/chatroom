package com.example.springboottutorial.id1212.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    User findUserByEmailAndPassword(String email,String password);

}
