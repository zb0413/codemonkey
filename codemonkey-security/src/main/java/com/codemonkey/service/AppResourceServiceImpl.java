package com.codemonkey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.codemonkey.domain.AppResource;
import com.codemonkey.repository.AppResourceRepository;

@Primary
@Service
public class AppResourceServiceImpl extends LogicalServiceImpl<AppResource , Long> implements AppResourceService {

	@Autowired private AppResourceRepository appResourceRepository;
	
	@Override
	public AppResource createEntity() {
		return new AppResource();
	}

	@Override
	public AppResourceRepository getRepository() {
		return appResourceRepository;
	}

}
