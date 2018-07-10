package com.codemonkey.service.log;

import org.aspectj.lang.JoinPoint;

import com.codemonkey.domain.LogRequest;
import com.codemonkey.service.GenericService;

public interface LogRequestService extends GenericService<LogRequest>{

	void saveLogRequest(JoinPoint joinPoint , Throwable throwable);


	

}
