package com.codemonkey.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.CmpPermission;
import com.codemonkey.service.CmpPermissionService;

@Controller
@RequestMapping("/ext/cmpPermission/**")
public class CmpPermissionController extends AbsBatchedController<CmpPermission>{

	@Autowired private CmpPermissionService cmpPermissionService;

	@Override
	protected CmpPermissionService service() {
		return cmpPermissionService;
	}

}
