package com.miage.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miage.business.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
}