package com.codemonkey;

import org.apache.activemq.broker.BrokerService;
import org.springframework.boot.SpringApplication;

public class CamelApplication extends CoreApp{

	
	public static void main(String[] args) throws Exception {
		
//		BrokerService broker = new BrokerService();
//		// configure the broker
//		broker.addConnector("tcp://localhost:61616");
//		broker.start();
		
		Object[] configures = new Object[]{
				"classpath*:/spring/applicationContext-*.xml",
				CamelApplication.class
		};
		SpringApplication.run(configures, args);
	}
	
}
