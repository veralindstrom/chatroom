package com.example.springboottutorial.id1212.entities.bridges;

import org.springframework.data.repository.CrudRepository;

public interface FileUserRepository extends CrudRepository<FileUser, String> {
    FileUser findFileUserByUser_id(Integer id);
    FileUser findFileUserByFile_id(Integer id);
}