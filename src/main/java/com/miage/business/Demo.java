package com.miage.business;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.miage.business.model.Person;
import com.miage.business.model.PersonGroup;
import com.miage.business.model.Role;
import com.miage.business.repository.GroupRepository;
import com.miage.business.repository.PersonRepository;
import com.miage.business.repository.RoleRepository;

//@Configuration
//@EnableAutoConfiguration
//@ComponentScan("com.miage.business")
//@EnableTransactionManagement
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

	@Override
	@Transactional
	public void run(String... arg0) throws Exception {
		// roles
		Role roleAdmin = roleRepository.findByName("ROLE_ADMIN");
		Role roleUser = roleRepository.findByName("ROLE_USER");
		if (roleAdmin == null) {
			roleAdmin = new Role("ROLE_ADMIN");
			roleRepository.save(roleAdmin);
			LOGGER.info("ROLE_ADMIN created");
		}
		if (roleUser == null) {
			roleUser = new Role("ROLE_USER");
			roleRepository.save(roleUser);
			LOGGER.info("ROLE_USER created");
		}

		Person person = null;
		List<Person> persons = personRepository.findByFirstNameAndLastName("John", "McLane");
		if (CollectionUtils.isEmpty(persons)) {
			person = new Person("John", "McLane");
	    	personRepository.save(person);
	    	LOGGER.info("Person created");
		}
		else {
			person = persons.get(0);
		}
		personRepository.updateRole(person, roleAdmin);
		
		PersonGroup personGroup1 =  groupRepository.findByName("Group1");
		if (personGroup1 == null) {
			personGroup1 = new PersonGroup("Group1");
	    	groupRepository.save(personGroup1);
	    	LOGGER.info("PersonGroup created");
		}
    	personRepository.addGroupToPerson(person, personGroup1);
//		personRepository.addPersonToGroup(personGroup1, person);
	}
}