package com.codemonkey.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.codemonkey.domain.AppRole;
import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.FormFieldValidation;
import com.codemonkey.repository.AppRoleRepository;
import com.codemonkey.utils.SysUtils;

@Primary
@Service
public class AppRoleServiceImpl extends LogicalServiceImpl<AppRole , Long> implements AppRoleService {

	@Autowired private AppRoleRepository appRoleRepository;
	
	@Override
	public AppRole createEntity() {
		return new AppRole();
	}

	@Override
	protected Set<FieldValidation> validate(AppRole appRole) {
		Set<FieldValidation> set = super.validate(appRole);
		if (SysUtils.isEmpty(appRole.getName())) {
			set.add(new FormFieldValidation("name", "角色名称不能为空！"));
		}

		if (!isUnique(appRole, "name", appRole.getName())) {
			set.add(new FormFieldValidation("name", "该名称已经存在，名称不能重复！"));
		}

		return set;
	}

	@Override
	public AppRoleRepository getRepository() {
		return appRoleRepository;
	}
}
