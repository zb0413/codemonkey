package com.codemonkey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemonkey.domain.FunctionNode;
import com.codemonkey.repository.FunctionNodeRepository;

@Service
public class FunctionNodeServiceImpl extends LogicalServiceImpl<FunctionNode , Long> implements FunctionNodeService {
	
	@Autowired private FunctionNodeRepository functionNodeRepository;
	
	@Override
	public FunctionNode createEntity() {
		return new FunctionNode();
	}

	@Override
	public FunctionNodeRepository getRepository() {
		return functionNodeRepository;
	}

}
