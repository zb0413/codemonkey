package com.codemonkey.init;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codemonkey.config.SecurityConfig;
import com.codemonkey.domain.AppRole;
import com.codemonkey.repository.AppRoleRepository;
import com.codemonkey.repository.FunctionNodeRepository;
import com.codemonkey.utils.SysUtils;

@Component
@InitOrder(8)
@Transactional
public class InitRoleServiceImpl implements InitDataBean{

	@Autowired private AppRoleRepository appRoleRepository;
	@Autowired private FunctionNodeRepository functionNodeRepository;
	@Autowired private SecurityConfig securityConfig;
	
	@Override
	public void doInit() {
		if(!SysUtils.fileExists(SysUtils.DATA_INITED)){
			AppRole adminRole = appRoleRepository.findByCode(securityConfig.getAdminRole());
			if (adminRole == null) {
				adminRole = new AppRole();
			}else{
				adminRole.setCode(securityConfig.getAdminRole());
				adminRole.setName("系统管理员");
			}
			appRoleRepository.save(adminRole);
		}
	}
}
