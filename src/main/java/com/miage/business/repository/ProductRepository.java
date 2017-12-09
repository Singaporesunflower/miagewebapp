package com.miage.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.miage.business.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByName(String name);
}