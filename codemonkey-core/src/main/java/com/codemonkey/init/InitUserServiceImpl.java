package com.codemonkey.init;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codemonkey.SysProp;
import com.codemonkey.domain.AppUser;
import com.codemonkey.service.AppRoleService;
import com.codemonkey.service.AppUserService;
import com.codemonkey.utils.SysUtils;

@Component
@InitOrder(10)
@Transactional
public class InitUserServiceImpl implements InitDataBean{

	@Autowired private AppUserService appUserService;
	@Autowired private AppRoleService appRoleService;
	@Autowired private SysProp sysProp;
	
	@Override
	public void doInit() {
		AppUser admin = appUserService.findBy("username", "admin");
		if (admin == null) {
			String salt = AppUser.generateSalt();
			admin = new AppUser();
			admin.setUsername("admin");
			admin.setName("admin");
			admin.setPassword(SysUtils.encrypt(sysProp.getPasswordEncrypt() , "admin" , salt));
			admin.setSalt(salt);
			admin.addAppRole(appRoleService.findBy("code", AppRoleService.ROLE_ADMIN));
			appUserService.save(admin);
		}

//		AppUser user = appUserService.findBy("username", "user");
//		if (user == null) {
//			String salt = AppUser.generateSalt();
//			user = new AppUser();
//			user.setUsername("user");
//			user.setName("user");
//			user.setPassword(SysUtils.encrypt(sysProp.getPasswordEncrypt() , "user" , salt));
//			user.setSalt(salt);
//			appUserService.save(user);
//		}
	}
}
