package com.miage.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miage.business.model.Category;
import com.miage.business.repository.CategoryRepository;
import com.miage.business.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public Category save(Category entity) {
		return categoryRepository.save(entity);
	}
	
	@Override
	public void delete(Long id) {
		categoryRepository.delete(id);
	}
	
	@Override
	public List<Category> findAll() {
		return (List<Category>) categoryRepository.findAll();
	}
	
	@Override
	public Category findById(Long id) {
		return categoryRepository.findOne(id);
	}
	
	@Override
	public List<Category> findByName(String name) {
		return categoryRepository.findByName(name);
	}
	
}
