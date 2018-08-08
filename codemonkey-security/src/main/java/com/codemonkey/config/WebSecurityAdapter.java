package com.codemonkey.config;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Getter
public class WebSecurityAdapter extends WebSecurityConfigurerAdapter{

	@Autowired private SecurityConfig securityConfig;
	
	@Autowired private UserDetailsServiceImpl userDetailsService;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	anonymousUrls(http);
    	authorizeRequests(http);
    	login(http);
    	logout(http);
    	csrf(http);
    	exceptionHandling(http);
    	
    }
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(securityConfig.getPasswordEncoder());
    }

	protected void exceptionHandling(HttpSecurity http)  throws Exception {
	}

	protected void csrf(HttpSecurity http) throws Exception{
		http.csrf().disable();
	}

	protected void logout(HttpSecurity http) throws Exception {
		http.logout().logoutUrl(securityConfig.getLogoutUrl()).permitAll()
        .and();
	}

	protected void login(HttpSecurity http) throws Exception {
	}

	protected void authorizeRequests(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated().and();
	}

	protected void anonymousUrls(HttpSecurity http) throws Exception {
		http.authorizeRequests().requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
		.and()
		.authorizeRequests().antMatchers(securityConfig.getAnonymousUrls()).permitAll()
		.and();
	}
}
