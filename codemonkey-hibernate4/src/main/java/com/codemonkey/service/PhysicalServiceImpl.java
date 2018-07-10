package com.codemonkey.service;

import java.util.List;

import org.apache.commons.lang.time.StopWatch;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import com.codemonkey.domain.IEntity;

@Transactional
public abstract class PhysicalServiceImpl<T extends IEntity> extends GenericServiceImpl<T> {
	
	public long count(){
		return getDao().count();
	}
	
	public T get(Long id) {
		T t = getDao().get(id);
		return t;
	}
	
	public List<T> findAll() {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		List<T> list = getDao().findAll();

		stopWatch.stop();
		getLog().info(stopWatch.getTime() + "ms");

		return list;
	}
	
	public T findBy(String query, Object... params) {

		T t = getDao().findBy(query, params);
		return t;
	}

	@Override
	public long countBy(String query, Object... params) {
		return getDao().countBy(query, params);
	}
	
	@Override
	public double sumBy(String field , String query, Object... params) {
		return getDao().sumBy(field , query , params);
	}

	public List<T> findAllBy(String query, Object... params) {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		List<T> list = getDao().findAllBy(query, params);

		stopWatch.stop();
		getLog().info(stopWatch.getTime() + "ms");

		return list;
	}

	public List<T> findByQueryInfo(JSONObject queryAndSort, Integer start,
			Integer limit) {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		List<T> list = getDao().findByQueryInfo(queryAndSort, start, limit);

		stopWatch.stop();
		getLog().info(stopWatch.getTime() + "ms");

		return list;

	}

	public List<T> findByQueryInfo(JSONObject queryInfo) {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		List<T> list = getDao().findByQueryInfo(queryInfo);

		stopWatch.stop();
		getLog().info(stopWatch.getTime() + "ms");

		return list;

	}

	public long countByQueryInfo(JSONObject queryInfo) {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		long count = getDao().countByQueryInfo(queryInfo);

		stopWatch.stop();
		getLog().info(stopWatch.getTime() + "ms");

		return count;

	}
	
	@Override
	public void delete(Long id) {

		T t = getDao().get(id);
		
		if(t != null){
			getDao().delete(id);
		}
	}


}
