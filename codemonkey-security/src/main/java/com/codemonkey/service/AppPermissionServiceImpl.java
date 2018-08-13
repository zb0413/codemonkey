package com.codemonkey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.codemonkey.domain.AppPermission;
import com.codemonkey.repository.AppPermissionRepository;

@Primary
@Service
public class AppPermissionServiceImpl extends LogicalServiceImpl<AppPermission , Long> implements AppPermissionService {

	@Autowired private AppPermissionRepository appPermissionRepository;
	
	@Override
	public AppPermission createEntity() {
		return new AppPermission();
	}

	@Override
	public AppPermissionRepository getRepository() {
		return appPermissionRepository;
	}

}
