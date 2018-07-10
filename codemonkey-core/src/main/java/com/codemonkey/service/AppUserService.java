package com.codemonkey.service;

import com.codemonkey.domain.AppUser;

public interface AppUserService extends GenericService<AppUser>{
	
	boolean hasRole(Long userid, Long id);
	
//	boolean login(String username, String password);
//	
//	void isAuth(String url);
	
//	AppUser doChangePassword(JSONObject body , FormattingConversionServiceFactoryBean ccService);
	
}
