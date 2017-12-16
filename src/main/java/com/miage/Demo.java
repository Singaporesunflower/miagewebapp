package com.miage;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.miage.business.model.Category;
import com.miage.business.model.Person;
import com.miage.business.model.PersonGroup;
import com.miage.business.model.Role;
import com.miage.business.repository.GroupRepository;
import com.miage.business.repository.PersonRepository;
import com.miage.business.repository.RoleRepository;
import com.miage.business.service.CategoryService;

@Component
public class Demo implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(Demo.class);

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	GroupRepository groupRepository;
	
	@Autowired
	CategoryService categoryService;

	@Override
	@Transactional
	public void run(String... arg0) throws Exception {
		// roles
		Role roleAdmin = roleRepository.findByName("ADMIN");
		Role roleUser = roleRepository.findByName("USER");
		if (roleAdmin == null) {
			roleAdmin = new Role("ADMIN");
			roleRepository.save(roleAdmin);
			LOGGER.info("ADMIN created");
		}
		if (roleUser == null) {
			roleUser = new Role("USER");
			roleRepository.save(roleUser);
			LOGGER.info("USER created");
		}

		// Person / roles
		Person personAdmin = personRepository.findByLogin("admin");
		if (personAdmin == null) {
			personAdmin = new Person("admin", "admin", "John", "McLane", "jmclane@hotmail.com");
	    	personRepository.save(personAdmin);
	    	LOGGER.info("Admin created");
	    	
			personRepository.updateRole(personAdmin, roleAdmin);
			LOGGER.info("admin role updated");
		}
		Person personUser = personRepository.findByLogin("user");
		if (personUser == null) {
			personUser = new Person("user", "user", "Pam", "Anderson", "pam@hotmail.com");
	    	personRepository.save(personUser);
	    	LOGGER.info("User created");
	    	
			personRepository.updateRole(personUser, roleUser);
			LOGGER.info("person role updated");
		}

		// Person / groups
		PersonGroup personGroup1 =  groupRepository.findByName("Group1");
		if (personGroup1 == null) {
			personGroup1 = new PersonGroup("Group1");
	    	groupRepository.save(personGroup1);
	    	LOGGER.info("PersonGroup created");
	    	
	    	personRepository.addGroupToPerson(personAdmin, personGroup1);
	    	LOGGER.info("group person added");
		}
		
		// Product categories
		addCategory("Fruit");
		addCategory("Légume");
		addCategory("Boisson");
	}
	
	private void addCategory(String name) {
		Category category =  categoryService.findByName(name);
		if (category == null) {
			category = new Category(name);
			categoryService.save(category);
			LOGGER.info("Category " + name + " created");
		}
	}
}