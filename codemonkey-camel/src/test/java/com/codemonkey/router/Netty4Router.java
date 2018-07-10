package com.codemonkey.router;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.codemonkey.beans.LoggerBean;

@Component
@Lazy(false)
public class Netty4Router extends RouteBuilder{
	
	public static final String URI = "mina2:tcp://localhost:1234?textline=true&sync=true";
	
	@Override
	public void configure() throws Exception {
		from(URI)
		.bean(LoggerBean.class)
		.process(new Processor() {
		    public void process(Exchange exchange) throws Exception {
		        String body = exchange.getIn().getBody(String.class);
		        exchange.getOut().setBody("Bye " + body);
		    }
		});
	}
}
