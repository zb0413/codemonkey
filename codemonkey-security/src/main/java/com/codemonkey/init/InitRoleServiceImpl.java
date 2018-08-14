package com.codemonkey.init;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codemonkey.config.SecurityConfig;
import com.codemonkey.domain.AppPermission;
import com.codemonkey.domain.AppResource;
import com.codemonkey.domain.AppRole;
import com.codemonkey.domain.FunctionNode;
import com.codemonkey.service.AppPermissionService;
import com.codemonkey.service.AppResourceService;
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
	@Autowired private AppPermissionService appPermissionService;
	@Autowired private AppResourceService appResourceService;
	
	@Override
	public void doInit() {
		if(!SysUtils.fileExists(SysUtils.DATA_INITED)){
			AppRole adminRole = appRoleService.findBy("code" , securityConfig.getAdminRole());
			if (adminRole == null) {
				adminRole = new AppRole();
			}
			adminRole.setCode(securityConfig.getAdminRole());
			adminRole.setName("系统管理员");
			List<FunctionNode> functionNodes = functionNodeService.findAll();
			addAllFunctionNodes(adminRole , functionNodes);
			appRoleService.save(adminRole);
			
			List<AppResource> appResources = appResourceService.findAll();
			addAllAppPermissions(adminRole , appResources);
		}
	}


	private void addAllAppPermissions(AppRole adminRole, List<AppResource> appResources) {
		if(SysUtils.isEmpty(appResources)) {
			return;
		}
	
		for(AppResource r : appResources) {
			AppPermission p = appPermissionService.findBy("role.id&&resource.id", adminRole.getId() , r.getId());
			if(p == null) {
				p = new AppPermission();
				p.setRole(adminRole);
				p.setResource(r);
				appPermissionService.save(p);
			}
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
