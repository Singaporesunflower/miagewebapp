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
		} catch (RuntimeException e) {
			throw new RepositoryException(e.getMessage());
		}
	}
	
	@Override
	@Transactional
	@Modifying
	public Person setRole(Person person, Role role) throws RepositoryException {
		try {
			person.setRole(role);
			
			Person rolePerson = role.getPersons().stream()
					.filter(x -> x.getId() == person.getId())
					.findFirst()
					.orElse(null);
			
			if (rolePerson == null) {
				role.getPersons().add(person);
			}
			else {
				rolePerson.setRole(role);
			}
			
			entityManager.persist(person);
			
			return person;
		} catch (RuntimeException e) {
			throw new RepositoryException(e.getMessage());
		}
	}

}
