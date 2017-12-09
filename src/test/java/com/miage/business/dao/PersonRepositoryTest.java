package com.miage.business.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.miage.business.SpringBusinessConfigTest;
import com.miage.business.model.Person;
import com.miage.business.model.Role;
import com.miage.business.repository.PersonRepository;
import com.miage.business.repository.RoleRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringBusinessConfigTest.class}) 
public class PersonRepositoryTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonRepositoryTest.class);
	
	private static final String FIRST_NAME = "John";
	private static final String LAST_NAME = "McLane";
	
    @Autowired
    PersonRepository personRepository;

    @Autowired
    RoleRepository roleRepository;
    
    @Test
    public void findByLastName_success() {
    	Person person = new Person(FIRST_NAME, LAST_NAME);
    	
    	person = personRepository.save(person);
    	
    	assertNotNull(person);
    	assertNotNull(person.getId());
    	
    	List<Person> entities = (List<Person>) personRepository.findByLastName(LAST_NAME);
    	assertNotNull(entities);
    	assertEquals(entities.size(), 1);
    	final Person expectedEntity = entities.get(0);
    	assertEquals(expectedEntity.getFirstName(), FIRST_NAME);
    	assertEquals(expectedEntity.getLastName(), LAST_NAME);
    	
    	LOGGER.info(">>>>> PersonRepository findByLastName_success OK");
    }
    
    @Test
    public void findByLastName_notFound() {
    	List<Person> entities = (List<Person>) personRepository.findByLastName("Gruber");
    	assertNotNull(entities);
    	assertEquals(entities.size(), 0);
    	
    	LOGGER.info(">>>>> PersonRepository findByLastName_notFound OK");
    }
    
    @Test
    @Transactional
    public void setRole_success() {
    	final Role role = new Role("ROLE");
    	roleRepository.save(role);
    	
    	final Person person = new Person("lastName", "firstName");
    	assertNull(person.getRole());
    	
    	// test saved returned person
    	final Person personSaved = personRepository.setRole(person, role);
    	assertEquals(personSaved.getRole(), role);
    	Person rolePersonSaved = role.getPersons().stream()
    			.filter(x -> x.getId() == person.getId())
    			.findFirst()
    			.orElse(null);
    	assertEquals(rolePersonSaved.getRole(), role);
    	
    	// test saved person in DB
    	final Person personDB = personRepository.findOne(person.getId());
    	final Role roleDB = roleRepository.findOne(role.getId());
    	assertEquals(personDB.getRole().getId(), roleDB.getId());
    	
    	Person rolePersonDB = roleDB.getPersons().stream()
    			.filter(x -> x.getId() == person.getId())
    			.findFirst()
    			.orElse(null);
    	assertEquals(rolePersonDB.getRole(), roleDB);
    	
    	LOGGER.info(">>>>> PersonRepository setRole_success OK");
    }
}