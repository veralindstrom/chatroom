package com.example.springboottutorial.id1212.entities.bridges;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_file")
public class FileUser {
    @Id
    private Integer user_id;
    @Id
    private Integer file_id;

    public void setFile_id(Integer file_id) {
        this.file_id = file_id;
    }

    public Integer getFile_id() {
        return file_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getUser_id() {
        return user_id;
    }
}

