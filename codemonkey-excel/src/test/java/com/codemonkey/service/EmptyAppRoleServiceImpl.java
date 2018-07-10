package com.codemonkey.service;

import org.springframework.stereotype.Service;

import com.codemonkey.domain.AppRole;

@Service
public class EmptyAppRoleServiceImpl extends EmptyGenericServiceImpl<AppRole> implements AppRoleService{

	@Override
	public boolean hasFunctionNode(Long roleid, Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
