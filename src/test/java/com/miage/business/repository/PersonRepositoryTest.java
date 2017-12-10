package com.miage.business.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.miage.business.SpringBusinessConfigTest;
import com.miage.business.model.Person;
import com.miage.business.model.PersonGroup;
import com.miage.business.model.Role;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringBusinessConfigTest.class })
public class PersonRepositoryTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonRepositoryTest.class);

	private static final String FIRST_NAME = "John";
	private static final String LAST_NAME = "McLane";
	private static final String ROLE1 = "Role1";
	private static final String ROLE2 = "Role2";
	private static final String GROUP = "Group";

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	PersonRepository personRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	GroupRepository groupRepository;

	@Before
	public void initObjects() {
		if (CollectionUtils.isEmpty(personRepository.findByFirstNameAndLastName(FIRST_NAME, LAST_NAME))) {
			personRepository.save(new Person(FIRST_NAME, LAST_NAME));
		}

		if (roleRepository.findByName(ROLE1) == null) {
			roleRepository.save(new Role(ROLE1));
		}
		
		if (roleRepository.findByName(ROLE2) == null) {
			roleRepository.save(new Role(ROLE2));
		}
		
		if (groupRepository.findByName(GROUP) == null) {
			groupRepository.save(new PersonGroup(GROUP));
		}
	}

	@Test
	public void findByLastName_success() {
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
	public void updateRole_success() {
		personRepository.findAll();
		final Person person = personRepository.findByFirstNameAndLastName(FIRST_NAME, LAST_NAME).get(0);
		assertNull(person.getRole());
		final Role role1 = roleRepository.findByName(ROLE1);
		final Role role2 = roleRepository.findByName(ROLE2);

		personRepository.updateRole(person, role1);
		assertEquals(person.getRole(), role1);
		assertTrue(role1.getPersons().contains(person));
		assertFalse(role2.getPersons().contains(person));

		personRepository.updateRole(person, role2);
		assertEquals(person.getRole(), role2);
		assertTrue(role2.getPersons().contains(person));
		assertFalse(role1.getPersons().contains(person));

		entityManager.flush();
		
		entityManager.refresh(role1);
		entityManager.refresh(role2);
		assertTrue(role2.getPersons().contains(person));
		assertFalse(role1.getPersons().contains(person));

		LOGGER.info(">>>>> PersonRepository updateRole_success OK");
	}

	@Test
	@Transactional
	public void addGroupToPerson_success() {
		final Person person = personRepository.findByFirstNameAndLastName(FIRST_NAME, LAST_NAME).get(0);
		final PersonGroup group = groupRepository.findByName(GROUP);

		assertEquals(person.getGroups().size(), 0);
		assertEquals(group.getPersons().size(), 0);

		personRepository.addGroupToPerson(person, group);
		assertEquals(person.getGroups().size(), 1);
		assertEquals(group.getPersons().size(), 0);
		
		entityManager.flush();
		
		entityManager.refresh(person);
		assertEquals(person.getGroups().size(), 1);

		LOGGER.info(">>>>> PersonRepository addGroupToPerson_success OK");
	}

	@Test
	@Transactional
	public void addPersonToGroup_success() {
		final Person person = personRepository.findByFirstNameAndLastName(FIRST_NAME, LAST_NAME).get(0);
		final PersonGroup group = groupRepository.findByName(GROUP);

		assertEquals(person.getGroups().size(), 0);
		assertEquals(group.getPersons().size(), 0);

		personRepository.addPersonToGroup(group, person);
		assertEquals(person.getGroups().size(), 0);
		assertEquals(group.getPersons().size(), 1);

		entityManager.flush();
		
		entityManager.refresh(group);
		assertEquals(group.getPersons().size(), 0);

		LOGGER.info(">>>>> PersonRepository addPersonToGroup_success OK");
	}
}