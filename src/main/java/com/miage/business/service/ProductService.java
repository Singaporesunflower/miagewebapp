package com.miage.business.service;

import java.util.List;

import com.miage.business.exception.ServiceException;
import com.miage.business.model.Product;

public interface ProductService {
    Product save(Product entity) throws ServiceException;
    void delete(Long id) throws ServiceException;
    List<Product> findAll() throws ServiceException;
    Product findById(Long id) throws ServiceException;
    List<Product> findByName(String name) throws ServiceException;
}
