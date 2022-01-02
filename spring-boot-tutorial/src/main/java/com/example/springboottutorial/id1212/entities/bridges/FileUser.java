package com.example.springboottutorial.id1212.entities.bridges;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="user_file")
public class FileUser implements Serializable {
    @Id
    private Integer userId;
    private Integer fileId;

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }
}

