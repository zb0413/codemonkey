package com.codemonkey.init;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codemonkey.config.SecurityConfig;
import com.codemonkey.domain.AppRole;
import com.codemonkey.domain.FunctionNode;
import com.codemonkey.service.AppRoleService;
import com.codemonkey.service.FunctionNodeService;
import com.codemonkey.utils.SysUtils;

@Component
@InitOrder(8)
@Transactional
public class InitRoleServiceImpl implements InitDataBean{

	@Autowired private AppRoleService appRoleService;
	@Autowired private FunctionNodeService functionNodeService;
	@Autowired private SecurityConfig securityConfig;
	
	@Override
	public void doInit() {
		if(!SysUtils.fileExists(SysUtils.DATA_INITED)){
			AppRole adminRole = appRoleService.findBy("code" , securityConfig.getAdminRole());
			if (adminRole == null) {
				adminRole = new AppRole();
			}else{
				adminRole.setCode(securityConfig.getAdminRole());
				adminRole.setName("系统管理员");
			}
			List<FunctionNode> functionNodes = functionNodeService.findAll();
			addAllFunctionNodes(adminRole , functionNodes);
			appRoleService.save(adminRole);
		}
	}

	private void addAllFunctionNodes(AppRole adminRole, List<FunctionNode> functionNodes) {
		Set<FunctionNode> set = adminRole.getFunctionNodes();
		
		if(SysUtils.isEmpty(set)) {
			if(SysUtils.isNotEmpty(functionNodes)) {
				set.addAll(functionNodes);
			}
		}
		
		if(SysUtils.isNotEmpty(functionNodes)) {
			for(FunctionNode fn : functionNodes) {
				if(SysUtils.isNotIncluded(functionNodes, fn)) {
					set.add(fn);
				}
			}
		}
	}
}
