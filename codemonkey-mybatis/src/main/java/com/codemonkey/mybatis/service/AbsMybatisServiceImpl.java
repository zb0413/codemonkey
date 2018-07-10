package com.codemonkey.mybatis.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;

import com.codemonkey.mybatis.dao.AbsMybatisDao;
import com.codemonkey.utils.SysUtils;

public abstract class AbsMybatisServiceImpl implements MybatisService{

	private Logger logger = SysUtils.getLog(getClass());
	
	public abstract AbsMybatisDao getDao();

	@Override
	public List<Map<String,Object>> query(String id , Object param){
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		List<Map<String,Object>> list = getDao().query(id , param);
		
		stopWatch.stop();
		logger.info(stopWatch.getTime() + "ms");
		
		return list;
	}
	
	@Override
	public long count(String id , Object param){
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		Long count = getDao().count(id, param);
		
		if(count == null){
			count = 0L;
		}
		
		stopWatch.stop();
		logger.info(stopWatch.getTime() + "ms");
		
		return count;
	}

	@Override
	public Long insert(String sqlId, Object params) {
		return getDao().insert(sqlId, params);
	}

	@Override
	public void update(String sqlId, Object params) {
		getDao().update(sqlId, params);
	}

	@Override
	public void delete(String sqlId, Object params) {
		getDao().delete(sqlId, params);
	}

	@Override
	public List<Map<String, Object>> find(String sqlId, Object params) {
		return getDao().query(sqlId , params);
	}
	
	@Override
	public Map<String, Object> findOne(String sqlId, Object params) {
		return getDao().findOne(sqlId , params);
	}
}
