package com.codemonkey.service;

import com.codemonkey.domain.GlobalConfig;

public interface GlobalConfigService extends GenericService<GlobalConfig>{
	
	String stringValue(String code);
	
	Double doubleValue(String code);
	
	Integer intValue(String code);

}
