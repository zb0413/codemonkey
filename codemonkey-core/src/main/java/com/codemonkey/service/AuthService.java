package com.codemonkey.service;

import com.codemonkey.domain.AppUser;


public interface AuthService {

	boolean doLogin(String username , String password);
	
	String ssoLogin();

	void doLogout();

	void isAuth(String url);
	
	String encryptPassword(String password , String salt);
	
	AppUser doChangePassword(AppUser user , String newPassword);
	
}
