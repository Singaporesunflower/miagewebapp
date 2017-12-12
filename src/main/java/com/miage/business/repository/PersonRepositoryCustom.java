package com.miage.business.repository;

import java.util.List;

import com.miage.business.model.PersonGroup;
import com.miage.business.model.Person;
import com.miage.business.model.Role;

public interface PersonRepositoryCustom {
    List<Person> findByRole2(String roleName);
    void updateRole(Person person, Role role);
    void addGroupToPerson(Person person, PersonGroup group);
    void addPersonToGroup(PersonGroup group, Person person);
}