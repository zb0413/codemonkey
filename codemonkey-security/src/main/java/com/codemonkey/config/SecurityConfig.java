package com.codemonkey.config;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="security")
@Getter
@Setter
public class SecurityConfig {

	private String[] anonymousUrls;
	
	private String loginUrl;
	
	private String logoutUrl;
	
	private String loginView;
	
	private String homeUrl;
	
	private String homeView;
	
	private String adminUsername;
	
	private String adminPassword;
	
	private String adminRole;
	
	public PasswordEncoder getPasswordEncoder(){
    	return new BCryptPasswordEncoder();
    }
	
}
