package com.codemonkey.camel;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CamelHelper {

	@Autowired
	private ProducerTemplate producerTemplate;
	
	@Autowired
	private ConsumerTemplate consumerTemplate;
	
	public Object requestBody(String endpoint , Object obj){
		Object result = producerTemplate.requestBody(endpoint , obj);
		return result;
	}
	
	public String http(String url){
		Exchange exchange = producerTemplate.send(url , new Processor() {
	         public void process(Exchange exchange) throws Exception {
	         }
		});
		Message out = exchange.getOut();
		String body = out.getBody(String.class);
		return body;
	}
	
}