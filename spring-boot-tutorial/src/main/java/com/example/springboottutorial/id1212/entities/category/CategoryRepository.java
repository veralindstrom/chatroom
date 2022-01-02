package com.example.springboottutorial.id1212.entities.category;

import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, String> {
    Category findCategoryByCategory_id(Integer id);
    Category findCategoryByCategory(String category);
}
