package com.eco.system.security;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    /**
     * Loads user details by username.
     * 
     * @param username the username to look up
     * @return the UserDetails object if found
     * @throws UsernameNotFoundException if the username is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // For demonstration purposes, a hard-coded username and password are used
        if ("pallavi".equals(username)) {
            // Create a UserDetails object with username, password, and authorities (empty list in this case)
            return new User("pallavi", "$2a$08$XmstlIV6l8z8lf3/O56af.5pF9n/wDTD6AuVFqfCjxZ7jqyG643tm",
                    new ArrayList<>());
        } else {
            // Throw an exception if the username is not found
            throw new UsernameNotFoundException(username + " is not found in the records");
        }
    }
}
