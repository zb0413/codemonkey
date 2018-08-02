package com.codemonkey.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUser extends User implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public SecurityUser(AppUser user) {
		//FixMe 
		this(user.getUsername(), user.getPassword(), null);
	}

}
