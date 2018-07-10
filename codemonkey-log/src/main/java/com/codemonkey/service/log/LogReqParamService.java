package com.codemonkey.service.log;

import com.codemonkey.domain.LogReqParam;
import com.codemonkey.domain.LogRequest;
import com.codemonkey.service.GenericService;

public interface LogReqParamService extends GenericService<LogReqParam>{

	void saveLotReqParam(LogRequest logRequest, Object arg);

	

}
