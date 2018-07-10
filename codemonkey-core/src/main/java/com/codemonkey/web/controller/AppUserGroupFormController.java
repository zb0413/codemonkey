package com.codemonkey.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.AppUserGroup;
import com.codemonkey.service.AppUserGroupService;

@Controller
@RequestMapping("/ext/appUserGroup/**")
public class AppUserGroupFormController extends AbsFormExtController<AppUserGroup>{

	@Autowired private AppUserGroupService appUserGroupService;
	
	@Override
	protected AppUserGroupService service() {
		return appUserGroupService;
	}
}
