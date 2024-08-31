package com.eco.system.security;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if ("pallavi".equals(username)) {
			return new User("pallavi", "$2a$08$XmstlIV6l8z8lf3/O56af.5pF9n/wDTD6AuVFqfCjxZ7jqyG643tm",
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException(username + " is not found in the records");
		}
	}
}
