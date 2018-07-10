package com.codemonkey.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.AppUser;
import com.codemonkey.service.AppUserService;

@Controller
@RequestMapping("/ext/appUserList/**")
public class AppUserListController extends AbsListExtController<AppUser>{

	@Autowired private AppUserService appUserService;
	
	@Override
	protected AppUserService service() {
		return appUserService;
	}

}
