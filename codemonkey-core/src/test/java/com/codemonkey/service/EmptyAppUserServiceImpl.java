package com.codemonkey.service;

import org.springframework.stereotype.Service;

import com.codemonkey.domain.AppUser;

@Service
public class EmptyAppUserServiceImpl extends EmptyGenericServiceImpl<AppUser> implements AppUserService{

	@Override
	public boolean hasRole(Long userid, Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
