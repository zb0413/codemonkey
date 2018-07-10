package com.codemonkey.service;

import org.springframework.stereotype.Service;

import com.codemonkey.domain.AppUserTaskHistory;

@Service
public class AppUserTaskHistoryServiceImpl extends LogicalServiceImpl<AppUserTaskHistory> implements AppUserTaskHistoryService{

	@Override
	public AppUserTaskHistory createEntity() {
		return new AppUserTaskHistory();
	}

}
