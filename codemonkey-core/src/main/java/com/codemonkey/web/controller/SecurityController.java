package com.codemonkey.web.controller;

import java.util.List;

import com.codemonkey.domain.AppPermission;
import com.codemonkey.domain.SecurityComponent;

public interface SecurityController {

	List<AppPermission> regAppPermissions();
	
	List<SecurityComponent> regSecurityComponents();
	
}
