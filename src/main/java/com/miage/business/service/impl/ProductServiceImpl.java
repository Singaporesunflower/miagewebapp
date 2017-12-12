package com.miage.business.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miage.business.exception.ServiceException;
import com.miage.business.model.Product;
import com.miage.business.repository.ProductRepository;
import com.miage.business.repository.impl.PersonRepositoryImpl;
import com.miage.business.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired
	ProductRepository categoryRepository;

	@Override
	public Product save(Product entity) throws ServiceException {
		return categoryRepository.save(entity);
	}
	
	@Override
	public void delete(Long id) throws ServiceException {
		categoryRepository.delete(id);
	}
	
	@Override
	public List<Product> findAll() throws ServiceException {
		return (List<Product>) categoryRepository.findAll();
	}
	
	@Override
	public Product findById(Long id) throws ServiceException {
		return categoryRepository.findOne(id);
	}
	
	@Override
	public Product findByName(String name) throws ServiceException {
		return categoryRepository.findByName(name);
	}
	
}
