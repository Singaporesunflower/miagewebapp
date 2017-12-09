package com.miage.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miage.business.model.Product;
import com.miage.business.repository.ProductRepository;
import com.miage.business.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductRepository categoryRepository;

	@Override
	public Product save(Product entity) {
		return categoryRepository.save(entity);
	}
	
	@Override
	public void delete(Long id) {
		categoryRepository.delete(id);
	}
	
	@Override
	public List<Product> findAll() {
		return (List<Product>) categoryRepository.findAll();
	}
	
	@Override
	public Product findById(Long id) {
		return categoryRepository.findOne(id);
	}
	
	@Override
	public List<Product> findByName(String name) {
		return categoryRepository.findByName(name);
	}
	
}
