package com.codemonkey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class SecurityJwtApp extends CoreApp{

	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder builder) {
		return super.configure(builder);
	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SecurityJwtApp.class, args);
	}
	
}
