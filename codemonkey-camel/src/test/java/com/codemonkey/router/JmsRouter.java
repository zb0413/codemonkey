package com.codemonkey.router;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.codemonkey.beans.LoggerBean;
import com.codemonkey.beans.ResultProcessBean;

@Component
@Lazy(false)
public class JmsRouter extends RouteBuilder{
	
	@Override
	public void configure() throws Exception {
		from("direct:test-jms")
			.bean(LoggerBean.class)
			.to("jms:queue:test-jms")
			.bean(ResultProcessBean.class)
			.to("file:/hello");
	}
}
