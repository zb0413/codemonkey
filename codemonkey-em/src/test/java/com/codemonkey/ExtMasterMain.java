package com.codemonkey;

import org.springframework.boot.SpringApplication;

public class ExtMasterMain extends CoreApp {
	
	public static void main(String[] args) throws Exception {
		
		Object[] configures = new Object[]{
				"classpath:/spring/applicationContext-dao.xml",
				"classpath:/spring/applicationContext-webapp.xml",
				ExtMasterMain.class
		};
		SpringApplication.run(configures, args);
	}
	
}
