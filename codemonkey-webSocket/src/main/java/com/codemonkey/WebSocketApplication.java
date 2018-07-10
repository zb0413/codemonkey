package com.codemonkey;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import com.codemonkey.sample.GreetingService;
import com.codemonkey.sample.GreetingWebSocketHandler;
import com.codemonkey.sample.SimpleGreetingService;


@EnableWebSocket
public class WebSocketApplication extends CoreApp implements WebSocketConfigurer{
	
	@Bean
	public GreetingService greetingService() {
		return new SimpleGreetingService();
	}
	
	@Bean
	public WebSocketHandler greetingWebSocketHandler() {
		return new GreetingWebSocketHandler(greetingService());
	}
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(greetingWebSocketHandler(), "/greeting").withSockJS();
		
	}
	
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
	
	public static void main(String[] args) throws Exception {
		
		Object[] configures = new Object[]{
				"classpath*:/spring/applicationContext-*.xml",
				WebSocketApplication.class
		};
		SpringApplication.run(configures, args);
	}

	
	
}
