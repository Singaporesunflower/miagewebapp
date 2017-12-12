package com.miage.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.miage.business.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
}