package com.miage.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.miage.business.model.Person;
import com.miage.business.model.Role;
import com.miage.business.service.PersonService;

//TODO enable Users in DB
@Service("securityUserDetailsService")
public class SecurityUserDetailsService implements UserDetailsService {
    
	@Autowired
    private PersonService personService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Person loggedInPerson = personService.findByLogin(login);
        if (loggedInPerson == null) {
            throw new UsernameNotFoundException(login);
        }
        
        List<GrantedAuthority> authorities = getAuthorities(loggedInPerson);
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        return new CustomUserDetails(
        		loggedInPerson.getLogin(),
        		loggedInPerson.getPassword(),
        		loggedInPerson.getFirstName(),
        		loggedInPerson.getLastName(),
        		loggedInPerson.getEmail(),
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                authorities);
    }
    
    private static List<GrantedAuthority> getAuthorities(Person person) {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        final Role role = person.getRole();
        authorities.add(new SimpleGrantedAuthority(role.getName()));
        return authorities;
    }

}