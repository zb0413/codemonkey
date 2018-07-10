package com.codemonkey.router;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.codemonkey.beans.ResultProcessBean;
import com.codemonkey.beans.LoggerBean;

@Component
@Lazy(false)
public class HttpRouter extends RouteBuilder{
	
	public static final String URL = "http://www.weather.com.cn/adat/sk/101110101.html";
	
	@Override
	public void configure() throws Exception {
		from("direct:test-http")
			.bean(LoggerBean.class)
			.to(URL)
			.bean(ResultProcessBean.class)
			.to("file:/hello");
	}
}
