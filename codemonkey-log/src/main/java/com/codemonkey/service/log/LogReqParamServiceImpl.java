package com.codemonkey.service.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemonkey.domain.LogReqParam;
import com.codemonkey.domain.LogRequest;
import com.codemonkey.service.AppUserService;
import com.codemonkey.service.LogicalServiceImpl;

@Service
public class LogReqParamServiceImpl extends LogicalServiceImpl<LogReqParam> implements LogReqParamService {

	
	@Autowired
	private AppUserService appUserService;

	
	@Override
	public LogReqParam createEntity() {
		LogReqParam logReqParam = new LogReqParam();
		return logReqParam;
	}
	
	@Override
	public
	void saveLotReqParam(LogRequest logRequest , Object arg){
		LogReqParam logReqParam = new LogReqParam();
		logReqParam.setLogRequest(logRequest);
		logReqParam.setParam(arg.toString());
		
	}
	
}
