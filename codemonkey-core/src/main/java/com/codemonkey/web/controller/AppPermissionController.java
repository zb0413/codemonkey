package com.codemonkey.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.AppPermission;
import com.codemonkey.service.AppPermissionService;

@Controller
@RequestMapping("/ext/appPermission/**")
public class AppPermissionController extends AbsBatchedController<AppPermission>{

	@Autowired private AppPermissionService appPermissionService;
	
	@Override
	protected AppPermissionService service() {
		return appPermissionService;
	}

}
