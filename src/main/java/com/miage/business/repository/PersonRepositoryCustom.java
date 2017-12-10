package com.miage.business.repository;

import java.util.List;

import com.miage.business.model.PersonGroup;
import com.miage.business.exception.RepositoryException;
import com.miage.business.model.Person;
import com.miage.business.model.Role;

public interface PersonRepositoryCustom {
    List<Person> findByRole2(String roleName) throws RepositoryException;
    void updateRole(Person person, Role role) throws RepositoryException;
    void addGroupToPerson(Person person, PersonGroup group) throws RepositoryException;
    void addPersonToGroup(PersonGroup group, Person person) throws RepositoryException;
}