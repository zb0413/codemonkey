package com.codemonkey.service;

import java.util.List;

import com.codemonkey.domain.AppRole;
import com.codemonkey.domain.FunctionNode;

public interface FunctionNodeService extends GenericService<FunctionNode>{

	List<FunctionNode> getFunctionNodesByRoles(List<AppRole> roles , Long rootId);
	
	List<FunctionNode> getFunctionNodesByRoles(List<AppRole> roles);
	
}
