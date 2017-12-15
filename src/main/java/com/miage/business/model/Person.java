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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.miage.utils.PasswordUtils;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"login"})})
public class Person implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	private Boolean active = Boolean.TRUE;
	
    @NotNull
    @Size(min=2, max=30)
    private String firstName;

    @NotNull
    @Size(min=2, max=30)
    private String lastName;
    
    @NotNull
    @Size(min=2, max=50)
    private String email;
    
    @NotNull
    @Size(min=2, max=20)
    private String login;
    
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
	
    public Person(String login, String password, String firstName, String lastName, String email) {
    	this.login = login;
		this.password = PasswordUtils.hashPassword(password);
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
