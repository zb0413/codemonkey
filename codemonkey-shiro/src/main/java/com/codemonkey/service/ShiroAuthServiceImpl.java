package com.codemonkey.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codemonkey.SysProp;
import com.codemonkey.domain.AppUser;
import com.codemonkey.domain.Status;
import com.codemonkey.domain.UrlPermission;
import com.codemonkey.utils.SysUtils;

@Component
public class ShiroAuthServiceImpl implements AuthService{
	
	@Autowired private UrlPermissionService urlPermissionService;
	@Autowired private AppUserService appUserService;
	@Autowired private SysProp sysProp;

	public boolean doLogin(AppUser user , String password){
		String pass = encryptPassword(password, user.getSalt());
		if (pass.equals(user.getPassword())) {
			UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername() , user.getPassword());
			SecurityUtils.getSubject().login(token);
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
				UsernamePasswordToken token = new UsernamePasswordToken(username, user.getPassword());
				SecurityUtils.getSubject().login(token);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String ssoLogin() {
		return SecurityUtils.getSubject().getPrincipal().toString();
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
		SecurityUtils.getSubject().logout();
	}

	@Override
	public void isAuth(String url) {
		UrlPermission permission = urlPermissionService.findBy("url_Like", url + "%");
	
		Subject subject = SecurityUtils.getSubject();
	
		if (permission != null && subject != null) {
			boolean notPermitted = !subject.isPermitted(permission
					.getPermission());
			if (notPermitted) {
				// throw new AuthError(permission.getUrl());
			}
		}
	}

}
