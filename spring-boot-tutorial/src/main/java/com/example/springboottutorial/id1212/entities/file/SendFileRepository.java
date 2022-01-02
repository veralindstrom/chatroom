package com.example.springboottutorial.id1212.entities.file;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface SendFileRepository extends CrudRepository<SendFile, String> {
    SendFile findChatFileByFile_id(Integer file_id);
    ArrayList<SendFile> findChatFilesByCategory(String category);
}