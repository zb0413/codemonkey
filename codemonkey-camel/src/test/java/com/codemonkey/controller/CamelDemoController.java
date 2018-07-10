package com.codemonkey.controller;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/camel/demo/**")
public class CamelDemoController {

	@Autowired private ProducerTemplate producerTemplate;
	
	@Autowired private ConsumerTemplate consumerTemplate;
	
	@RequestMapping("index")
	public String index(){
		return "index";
	}
	
	@RequestMapping("/sendJms")
	public String sendJms(ModelMap modelMap){
		modelMap.put("msg", "发送异步消息");
		producerTemplate.requestBody("direct:test-jms", "Test Message: " + 1);
		return "index";
	}
	
	@RequestMapping("/sendJmsSync")
	public String sendJmsSync(ModelMap modelMap){
		//TODO 接收消息有错误
		modelMap.put("msg", "发送同步消息");
		producerTemplate.send("direct:test-jms-sync", 
			new Processor() {
			    public void process(Exchange exchange) throws Exception {
			        // and here we override the destination with the ReplyTo destination object so the message is sent to there instead of dummy
			        exchange.getIn().setHeader("JMSReplyTo" , "bar");
			        exchange.getIn().setHeader("CamelRedelivered" , true);
//			        exchange.getIn().setHeader("JMSCorrelationID" , exchange.getj);
			        
			        exchange.getIn().setBody("Here is the late reply.");
			    }
		    }
		);
		return "index";
	}
	
	@RequestMapping("/sendHttp")
	public String sendHttp(ModelMap modelMap){
		modelMap.put("msg", "发送http请求");
		producerTemplate.send(
				"direct:test-http" ,  
				new Processor() {
				    public void process(Exchange exchange) throws Exception {
				    }
			    }
		);
		return "index";
	}
	
	@RequestMapping("/sendWebService")
	public String sendWebService(ModelMap modelMap){
		modelMap.put("msg", "发送WebService请求");
		producerTemplate.send(
				"direct:test-webService" ,  
				new Processor() {
				    public void process(Exchange exchange) throws Exception {
			    	    exchange.getIn().setHeader(Exchange.HTTP_QUERY, "CityName=dalian&CountryName=china");
				    }
			    }
		);
		return "index";
	}
	
}
