package com.codemonkey.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.web.controller.AbsController;

@Controller
public class DemoController extends AbsController{

	@RequestMapping("/welcome")
	public String welcome(Map<String, Object> model) {
		return "welcome";
	}
	
	@RequestMapping("/ext/demo")
	public String extDemo(Map<String, Object> model) {
		return "ext/demo";
	}
	
	@RequestMapping("/bootstrap/demo")
	public String bootstrapDemo(Map<String, Object> model) {
		return "bootstrap/demo";
	}
	
	@RequestMapping("/bootstrap/demo/{group}/{page}")
	public String toPage(Map<String, Object> model , @PathVariable String group , @PathVariable String page) {
		return "bootstrap/" + group + "/" + page;
	}
	
	@RequestMapping("/flow")
	public String flow(Map<String, Object> model) {
		return "flow";
	}
	
	@RequestMapping("/login")
	public String login(Map<String, Object> model) {
		return "login";
	}

}