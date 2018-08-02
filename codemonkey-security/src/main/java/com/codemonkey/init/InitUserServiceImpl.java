package com.codemonkey.init;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codemonkey.config.SecurityConfig;
import com.codemonkey.domain.AppRole;
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
	@Autowired private SecurityConfig securityConfig;
	
	@Override
	public void doInit() {
		if(!SysUtils.fileExists(SysUtils.DATA_INITED)){
			AppUser admin = appUserService.findBy("username" , securityConfig.getAdminUsername());
			if (admin == null) {
				admin = new AppUser();
			}else{
				admin.setUsername(securityConfig.getAdminUsername());
				admin.setRawPassword(securityConfig.getAdminPassword());
			}
			
			boolean hasAdminRole = false;
			Set<AppRole> roles = admin.getRoles();
			if(SysUtils.isNotEmpty(roles)){
				for(AppRole r : roles){
					if(securityConfig.getAdminRole().equals(r.getCode())){
						hasAdminRole = true;
						break;
					}
				}
			}
			
			if(!hasAdminRole){
				AppRole role = appRoleService.findBy("code", securityConfig.getAdminRole());
				admin.getRoles().add(role);
			}
			
			appUserService.save(admin);
		}
	}
}
