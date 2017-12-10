package com.miage.business.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Person implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Boolean active = Boolean.FALSE;
	
    @NotNull
    @Size(min=2, max=30)
    private String firstName;

    @NotNull
    @Size(min=2, max=30)
    private String lastName;
    
    @Size(min=10, max=20)
    private String login;
    
    @Size(min=8, max=20)
    private String password;
    
    @Min(18)
    private Integer age;

    // This field exists in the DB
    // Person contains a foreign key to role
	@ManyToOne
	private Role role;
	
	@ManyToMany
	private List<PersonGroup> groups = new ArrayList<>(0);
    
    public Person() {
	}	
	
    public Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public List<PersonGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<PersonGroup> groups) {
		this.groups = groups;
	}

	public String toString() {
    	final StringBuilder sb = new StringBuilder();
    	sb.append("Person(").append(this.lastName).append(", ").append(this.firstName).append(", age:").append(this.age).append(")");
    	return sb.toString();
    }
	
}
