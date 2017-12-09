package com.miage.business.service;

import java.util.List;

import com.miage.business.model.Category;

public interface CategoryService {
    Category save(Category entity);
    void delete(Long id);
    List<Category> findAll();
    Category findById(Long id);
    List<Category> findByName(String name);
}
