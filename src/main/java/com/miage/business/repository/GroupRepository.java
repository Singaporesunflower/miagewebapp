package com.miage.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miage.business.model.PersonGroup;

public interface GroupRepository extends JpaRepository<PersonGroup, Long> {
	PersonGroup findByName(String name);
}