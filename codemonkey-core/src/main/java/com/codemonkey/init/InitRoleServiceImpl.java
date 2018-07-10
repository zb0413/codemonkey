package com.codemonkey.init;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	
	
	@Override
	public void doInit() {

		AppRole adminRole = appRoleService.findBy("code", AppRoleService.ROLE_ADMIN);
		if (adminRole == null) {
			adminRole = new AppRole();
			adminRole.setCode(AppRoleService.ROLE_ADMIN);
			adminRole.setName("系统管理员");
			appRoleService.save(adminRole);
		}
		
		List<FunctionNode> functionNodes = functionNodeService.findAll();
		Set<FunctionNode> oldNodes = adminRole.getFunctionNodes();
		if(SysUtils.isNotEmpty(functionNodes)){
			//比较现有功能点是否有新增的
			for(FunctionNode node : functionNodes){
				Long id = node.getId();
				boolean flag = true;
				
				if(SysUtils.isNotEmpty(oldNodes)){
					for(FunctionNode oldNode : oldNodes){
						Long oldId =  oldNode.getId();
						 if(id.equals(oldId)){
							flag = false;
							break;
						 }
					}
				}
				
				if(flag){
					adminRole.addFunctionNode(node);
				}
			}
			appRoleService.save(adminRole);
		}
	}
}
