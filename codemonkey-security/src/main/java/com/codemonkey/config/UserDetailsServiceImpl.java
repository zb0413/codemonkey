package com.codemonkey.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.codemonkey.domain.AppUser;
import com.codemonkey.repository.AppUserRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser user = appUserRepository.findByUsername(username);
        return new SecurityUserDetails(user);
    }
}
