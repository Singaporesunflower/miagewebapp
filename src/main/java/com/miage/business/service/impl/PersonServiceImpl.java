package com.miage.business.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miage.business.exception.ServiceException;
import com.miage.business.model.Person;
import com.miage.business.repository.PersonRepository;
import com.miage.business.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PersonServiceImpl.class);
	
	@Autowired
	PersonRepository personRepository;

	@Override
	public Person save(Person entity) throws ServiceException {
		return personRepository.save(entity);
	}
	
	@Override
	public void delete(Long id) throws ServiceException {
		personRepository.delete(id);
	}
	
	@Override
	public List<Person> findAll() throws ServiceException {
		return (List<Person>) personRepository.findAll();
	}
	
	@Override
	public Person findById(Long id) throws ServiceException {
		return personRepository.findOne(id);
	}
	
	@Override
	public Person findByLogin(String login) {
		return personRepository.findByLogin(login);
	}
	
	@Override
	public List<Person> findByFirstNameAndLastName(String firstName, String lastName) throws ServiceException {
		return personRepository.findByFirstNameAndLastName(firstName, lastName);
	}
	
}
