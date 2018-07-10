package com.codemonkey.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.codemonkey.domain.AppRole;
import com.codemonkey.domain.FunctionNode;

@Service
public class EmptyFunctionNodeServiceImpl extends EmptyGenericServiceImpl<FunctionNode> implements FunctionNodeService{

	@Override
	public List<FunctionNode> getFunctionNodesByRoles(List<AppRole> roles,
			Long rootId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FunctionNode> getFunctionNodesByRoles(List<AppRole> roles) {
		// TODO Auto-generated method stub
		return null;
	}


}
