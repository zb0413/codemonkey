package com.codemonkey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.codemonkey.SysProp;
import com.codemonkey.domain.AppUser;
import com.codemonkey.domain.Status;
import com.codemonkey.utils.SysUtils;

@Component
public class ShiroAuthServiceImpl implements AuthService{
	
	@Autowired private UrlPermissionService urlPermissionService;
	@Autowired private AppUserService appUserService;
	@Autowired private SysProp sysProp;
	@Autowired private AuthenticationManager authenticationManager;
	@Autowired private SecurityContextHolder securityContextHolder;
	

	public boolean doLogin(AppUser user , String password){
		String pass = encryptPassword(password, user.getSalt());
		if (pass.equals(user.getPassword())) {
			
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername() , user.getPassword());
			authenticationManager.authenticate(token);
			// SysUtils.putAttribute(SysUtils.CURRENCT_USER, username);
			// SysUtils.putAttribute(SysUtils.CURRENCT_USER_LOGININFO,
			// user);
			return true;
		}
		
		return false;
	}

	@Override
	public boolean doLogin(String username, String password) {
		AppUser user = appUserService.findBy("username&&status", username, Status.启用);

		if (user != null) {
			String pass = encryptPassword(password, user.getSalt());
			if (pass.equals(user.getPassword())) {
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, user.getPassword());
				authenticationManager.authenticate(token);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String ssoLogin() {
		//TODO sso 将来实现
		return null;
	}
	
	public AppUser doChangePassword(AppUser user , String newPassword){
		
		String hashedPasswordBase64 = encryptPassword(newPassword , user.getSalt());
		user.setPassword(hashedPasswordBase64);
		appUserService.save(user);
		
		return user;
	        
	}
	
	@Override
	public String encryptPassword(String password , String salt) {
		return SysUtils.encrypt(sysProp.getPasswordEncrypt() , password, salt);
	}

	@Override
	public void doLogout() {
		
	}

	@Override
	public void isAuth(String url) {
		//没有必要实现
	}

}
