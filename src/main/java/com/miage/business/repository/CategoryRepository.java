package com.miage.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.miage.business.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}