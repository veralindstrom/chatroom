package com.example.springboottutorial.id1212.entities.file;

import javax.persistence.*;
import java.io.File;

@Entity
@Table(name="file")
public class SendFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer file_id;
    private File file;
    private String category;

    public Integer getFile_id() {
        return file_id;
    }

    public void setFile_id(Integer file_id) {
        this.file_id = file_id;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getCategory(){ return category; }

    public void setCategory(String category) { this.category = category; }
}
