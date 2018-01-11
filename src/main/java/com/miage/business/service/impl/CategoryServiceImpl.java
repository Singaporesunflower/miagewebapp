package com.miage.business.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miage.business.exception.ServiceException;
import com.miage.business.model.Category;
import com.miage.business.repository.CategoryRepository;
import com.miage.business.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);
	
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public Category create(Category entity) throws ServiceException {
		return categoryRepository.save(entity);
	}
	
	@Override
	public Category update(Long id, Category entity) throws ServiceException {
		final Category category = this.categoryRepository.findOne(id);
		category.setName(entity.getName());
		return categoryRepository.save(entity);
	}
	
	@Override
	public void delete(Long id) throws ServiceException {
		categoryRepository.delete(id);
	}
	
	@Override
	public List<Category> findAll() throws ServiceException {
		return (List<Category>) categoryRepository.findAll();
	}
	
	@Override
	public Category findById(Long id) throws ServiceException {
		return categoryRepository.findOne(id);
	}
	
	@Override
	public Category findByName(String name) throws ServiceException {
		return categoryRepository.findByName(name);
	}
	
}
