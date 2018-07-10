package com.codemonkey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class CmsApplication extends CoreApp {
	
	public static void main(String[] args) throws Exception {
		
		Object[] configures = new Object[]{
				"classpath:/spring/applicationContext-dao.xml",
				"classpath:/spring/applicationContext-webapp.xml",
				CmsApplication.class
		};
		SpringApplication.run(configures, args);
	}
	
}
