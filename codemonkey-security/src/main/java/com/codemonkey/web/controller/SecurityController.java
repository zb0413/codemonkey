package com.codemonkey.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.codemonkey.config.SecurityConfig;

@Controller
public class SecurityController {

	@Autowired private SecurityConfig securityConfig;
	
	@GetMapping("/home")
	public String home(){
		return securityConfig.getHomeView();
	}
	
	@GetMapping("/")
	public String root(){
		return securityConfig.getHomeView();
	}
	
	@GetMapping(value="/login")
	public String login(ModelMap map){
		map.addAttribute("loginUrl", securityConfig.getLoginUrl());
		return securityConfig.getLoginView();
	}
}
