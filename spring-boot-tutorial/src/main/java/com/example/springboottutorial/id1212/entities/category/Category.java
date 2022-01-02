package com.example.springboottutorial.id1212.entities.category;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer categoryId;
    private String category;

    public Integer getcategoryId() {
        return categoryId;
    }

    public void setcategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }
}
