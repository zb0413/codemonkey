package com.codemonkey.mybatis.service;

import java.util.List;
import java.util.Map;

public interface MybatisService {

	List<Map<String, Object>> query(String sqlId, Object params);

	long count(String sqlId, Object params);
	
	Long insert(String sqlId , Object params);
	
	void update(String sqlId , Object params);
	
	void delete(String sqlId , Object params);
	
	List<Map<String, Object>> find(String sqlId , Object params);
	
	Map<String, Object> findOne(String sqlId , Object params);

}