package com.miage.business.service;

import java.util.List;

import com.miage.business.model.Product;

public interface ProductService {
    Product save(Product entity);
    void delete(Long id);
    List<Product> findAll();
    Product findById(Long id);
    Product findByName(String name);
}
