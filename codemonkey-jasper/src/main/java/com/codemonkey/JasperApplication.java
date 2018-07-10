package com.codemonkey;

import org.springframework.boot.SpringApplication;

public class JasperApplication extends CoreApp {
	
	public static void main(String[] args) throws Exception {
		
		Object[] configures = new Object[]{
				"classpath:/spring/applicationContext-dao.xml",
				"classpath:/spring/applicationContext-jasper.xml",
				JasperApplication.class
		};
		SpringApplication.run(configures, args);
	}

}
