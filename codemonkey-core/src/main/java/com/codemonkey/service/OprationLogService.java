package com.codemonkey.service;

import org.json.JSONObject;

import com.codemonkey.domain.OprationLog;

public interface OprationLogService extends GenericService<OprationLog>{
	
	public void saveLog(String oprationType , String entityId, String msg);
	
	public String getLogMessage(JSONObject oldInfo, JSONObject newInfo , String name ,String desc, Boolean flag);
}
