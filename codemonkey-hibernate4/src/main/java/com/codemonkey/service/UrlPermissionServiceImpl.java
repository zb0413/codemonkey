package com.codemonkey.service;

import org.springframework.stereotype.Service;

import com.codemonkey.domain.UrlPermission;

@Service
public class UrlPermissionServiceImpl extends PhysicalServiceImpl<UrlPermission> implements UrlPermissionService{

	@Override
	public UrlPermission createEntity() {
		return new UrlPermission(null , null , null , null);
	}

}
