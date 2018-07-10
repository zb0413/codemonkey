package com.codemonkey.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.codemonkey.camel.CamelHelper;

public class CamelHelperTest extends CamelTest{

	@Autowired private CamelHelper camelHelper;
	
	@Test
	public void testJms(){
//		String result = (String) camelHelper.requestBody("direct:test-jms", "hello world");
//		assertEquals("hello world" , result);
	}
	
	@Test
	public void testHttp(){
		String out = camelHelper.http("http://www.weather.com.cn/adat/sk/101110101.html");
		out.toString();
	}
}
