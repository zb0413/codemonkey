package com.codemonkey.service;

import org.springframework.stereotype.Service;

import com.codemonkey.domain.AppPermission;

@Service
public class AppPermissionServiceImpl extends PhysicalServiceImpl<AppPermission> implements AppPermissionService{

	@Override
	public AppPermission createEntity() {
		return new AppPermission(null , null);
	}

}
