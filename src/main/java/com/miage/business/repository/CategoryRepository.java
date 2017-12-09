package com.miage.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.miage.business.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByName(String name);
}