package com.miage.business.repository;

import java.util.List;

import com.miage.business.model.Person;
import com.miage.business.model.Role;

public interface PersonRepositoryCustom {
    List<Person> findByRole2(String roleName);
    Person setRole(Person person, Role role);
}