package com.codemonkey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemonkey.domain.GlobalConfig;
import com.codemonkey.repository.GlobalConfigRepository;

@Service
public class GlobalConfigServiceImpl  extends LogicalServiceImpl<GlobalConfig , Long> implements GlobalConfigService {

	@Autowired private GlobalConfigRepository globalConfigRepository;
	
	@Override
	public GlobalConfig createEntity() {
		return new GlobalConfig();
	}

	@Override
	public GlobalConfigRepository getRepository() {
		return globalConfigRepository;
	}

}
