package com.codemonkey.web.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.AppRole;
import com.codemonkey.domain.AppUser;
import com.codemonkey.service.AppRoleService;
import com.codemonkey.service.AppUserService;
import com.codemonkey.utils.SysUtils;

@Controller
@RequestMapping("/ext/appUser/**")
public class AppUserFormController extends AbsFormExtController<AppUser>{

	@Autowired private AppUserService appUserService;
	@Autowired private AppRoleService appRoleService;
	
	@Override
	protected AppUserService service() {
		return appUserService;
	}
	
	@Override
	public JSONObject jsonResult(AppUser t) {
		JSONObject result = super.jsonResult(t);
		// 取出所有角色
		List<AppRole> roleAll = appRoleService.findAll();
		JSONArray ja = new JSONArray();
		if(SysUtils.isNotEmpty(roleAll)){
			for(AppRole role :roleAll){
				ja.put(role.listJson());
			}
		}
		result.put("lines", ja);
		return result;
	}
	
}

