package com.codemonkey;

import org.springframework.boot.SpringApplication;


public class WebfaceApplication extends CoreApp {
	
	public static void main(String[] args) throws Exception {
		
		Object[] configures = new Object[]{
				"classpath*:/spring/applicationContext-*.xml",
				WebfaceApplication.class
		};
		SpringApplication.run(configures, args);
	}
	
}
