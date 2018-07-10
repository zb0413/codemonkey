package com.codemonkey.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.UrlPermission;
import com.codemonkey.service.UrlPermissionService;

@Controller
@RequestMapping("/ext/urlPermission/**")
public class UrlPermissionController extends AbsBatchedController<UrlPermission>{

	@Autowired private UrlPermissionService urlPermissionService;
	
	@Override
	protected UrlPermissionService service() {
		return urlPermissionService;
	}

}
