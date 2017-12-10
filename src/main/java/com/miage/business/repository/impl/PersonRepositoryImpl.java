package com.miage.business.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.miage.business.exception.RepositoryException;
import com.miage.business.model.PersonGroup;
import com.miage.business.model.Person;
import com.miage.business.model.Role;
import com.miage.business.repository.PersonRepositoryCustom;

public class PersonRepositoryImpl implements PersonRepositoryCustom {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonRepositoryImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Person> findByRole2(String roleName) throws RepositoryException {
		String queryJPQL = "select p from Person p where p.role.name = :roleName order by p.lastName, p.firstName";

		final TypedQuery<Person> query = entityManager.createQuery(queryJPQL, Person.class);
		query.setMaxResults(10);
		query.setParameter("roleName", roleName);

		try {
			return query.getResultList();
		}
		catch (RuntimeException e) {
			throw new RepositoryException(e.getMessage());
		}
	}
	
	@Override
	@Transactional
	@Modifying
	public void updateRole(Person person, Role role) throws RepositoryException {
		try {
			// For the database it is sufficient to set the owning side of the relationship
			// It's the developper's responsibility to set correct relations in memory
			
			// delete the old relation role person if exists
			if (person.getRole() != null && person.getRole().getPersons() != null && person.getRole().getPersons().contains(person)) {
				person.getRole().getPersons().remove(person);
			}
			
			// set the new role
			person.setRole(role);
			// update the bidirectional relation : add person to the role persons list
			role.getPersons().add(person);
	
			// persist the person will persist the role
			entityManager.persist(person);

		}
		catch (RuntimeException e) {
			throw new RepositoryException(e.getMessage());
		}
	}

	@Override
	@Modifying
	public void addGroupToPerson(Person person, PersonGroup group) throws RepositoryException {
		try {
			person.getGroups().add(group);
			entityManager.persist(person);
		}
		catch (RuntimeException e) {
			throw new RepositoryException(e.getMessage());
		}
	}
	
	@Override
	@Transactional
	@Modifying
	public void addPersonToGroup(PersonGroup group, Person person) throws RepositoryException {
		try {
			group.getPersons().add(person);
			entityManager.persist(group);
		}
		catch (RuntimeException e) {
			throw new RepositoryException(e.getMessage());
		}
	}
	
}
