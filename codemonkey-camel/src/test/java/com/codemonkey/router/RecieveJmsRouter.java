package com.codemonkey.router;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.codemonkey.beans.LoggerBean;
import com.codemonkey.beans.ResultProcessBean;

@Component
@Lazy(false)
public class RecieveJmsRouter extends RouteBuilder{
	
	@Override
	public void configure() throws Exception {
		from("jms:queue:test-jms?concurrentConsumers=1")
			.bean(LoggerBean.class)
			.bean(ResultProcessBean.class)
			.to("file:/hello");
	}
}
