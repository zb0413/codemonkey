package com.codemonkey.web.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.AppRole;
import com.codemonkey.domain.AppUser;
import com.codemonkey.service.AppRoleService;
import com.codemonkey.utils.SysUtils;

@Controller
@RequestMapping("/ext/appRoleList/**")
public class AppRoleListController extends AbsListExtController<AppRole> {

	@Autowired
	private AppRoleService appRoleService;

	@Override
	protected AppRoleService service() {
		return appRoleService;
	}

	@Override
	protected String handleRead(Integer start, Integer limit, String id,
			String query, JSONArray sort, JSONObject queryInfo) {

		AppUser user = SysUtils.getCurrentUser();
		if (!user.isAdmin()) {
			queryInfo.put("code_isNull", "");
		}

		return super.handleRead(start, limit, id, query, sort, queryInfo);
	}

}
