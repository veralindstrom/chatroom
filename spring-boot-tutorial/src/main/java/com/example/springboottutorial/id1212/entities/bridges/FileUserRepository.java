package com.example.springboottutorial.id1212.entities.bridges;

import org.springframework.data.repository.CrudRepository;

public interface FileUserRepository extends CrudRepository<FileUser, Integer> {
    FileUser findFileUserByUserId(Integer userId);
    FileUser findFileUserByFileId(Integer fileId);
}