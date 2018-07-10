package com.codemonkey.service;

import com.codemonkey.domain.AppRole;

public interface AppRoleService extends GenericService<AppRole>{

	String ROLE_ADMIN = "ROLE_ADMIN";

	boolean hasFunctionNode(Long roleid, Long id);

}
