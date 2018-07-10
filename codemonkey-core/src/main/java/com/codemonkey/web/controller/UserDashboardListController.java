package com.codemonkey.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.UserDashboard;
import com.codemonkey.service.UserDashboardService;

@Controller
@RequestMapping("/ext/userDashboardList/**")
public class UserDashboardListController extends AbsBatchedController<UserDashboard>{

	@Autowired private UserDashboardService userDashboardService;
	
	@Override
	protected UserDashboardService service() {
		return userDashboardService;
	}

}
