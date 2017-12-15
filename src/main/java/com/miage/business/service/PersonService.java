package com.miage.business.service;

import java.util.List;

import com.miage.business.model.Person;

public interface PersonService {
    Person save(Person entity);
    void delete(Long id);
    List<Person> findAll();
    Person findById(Long id);
    Person findByLogin(String login);
    List<Person> findByFirstNameAndLastName(String firstName, String lastName);
}
