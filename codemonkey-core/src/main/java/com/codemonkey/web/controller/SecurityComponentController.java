package com.codemonkey.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.SecurityComponent;
import com.codemonkey.service.SecurityComponentService;

@Controller
@RequestMapping("/ext/securityComponent/**")
public class SecurityComponentController extends AbsBatchedController<SecurityComponent>{

	@Autowired private SecurityComponentService securityComponentService;
	
	@Override
	protected SecurityComponentService service() {
		return securityComponentService;
	}

}
