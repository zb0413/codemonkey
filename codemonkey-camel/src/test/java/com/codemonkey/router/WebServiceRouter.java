package com.codemonkey.router;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.codemonkey.beans.LoggerBean;
import com.codemonkey.beans.ResultProcessBean;

@Component
@Lazy(false)
public class WebServiceRouter extends RouteBuilder{
	
	public static final String WEATHER_URI = "http://www.webservicex.net/globalweather.asmx/GetWeather";
	
	@Override
	public void configure() throws Exception {
		from("direct:test-webService")
			.bean(LoggerBean.class)
			.to(WEATHER_URI)
			.bean(ResultProcessBean.class).to("file:/weather");
	}
}
