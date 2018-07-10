package com.codemonkey.router;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.codemonkey.beans.ResultProcessBean;
import com.codemonkey.beans.LoggerBean;

@Component
@Lazy(false)
public class JmsSyncRouter extends RouteBuilder{
	
	@Override
	public void configure() throws Exception {
		from("direct:test-jms-sync")
			.bean(LoggerBean.class)
			.inOut("jms:queue:test-jms-sync?jmsMessageType=Text")
			.bean(ResultProcessBean.class)
			.to("file:/test-jms-sync");
	}
}
