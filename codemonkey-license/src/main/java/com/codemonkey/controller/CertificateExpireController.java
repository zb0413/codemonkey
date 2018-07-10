package com.codemonkey.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.codemonkey.web.controller.AbsController;

@Controller
public class CertificateExpireController extends AbsController{

	WebMvcConfigurerAdapter adapter;
	
	@RequestMapping("/certificateExpire")
	public String certificateExpire() {
		return "certificateExpire";
	}
}
