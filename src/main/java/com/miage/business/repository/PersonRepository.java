package com.miage.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.miage.business.model.Person;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long>, PersonRepositoryCustom {

	List<Person> findByLastName(String lastName);
	List<Person> findByLastNameAndFirstName(String lastName, String firstName);
	
    @Query("select p from Person p where p.role.name = :roleName order by p.lastName, p.firstName")
    List<Person> findByRole(@Param("roleName") String roleName);

    // pour dire à spring-data-jpa que cette query est une opération update et qu'elle requiert executeUpdate() et non executeQuery()
    @Modifying
    @Query("update Person p set p.active = :active where p.id = :id")
    void activate(@Param("active") Boolean active, @Param("id") Long id);

}