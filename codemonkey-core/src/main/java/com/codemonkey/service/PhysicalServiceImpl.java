package com.codemonkey.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.time.StopWatch;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import com.codemonkey.domain.IEntity;

@Transactional
public abstract class PhysicalServiceImpl<T extends IEntity<ID> , ID extends Serializable> extends GenericServiceImpl<T , ID> {
	
	public long count(){
		return getRepository().count();
	}
	
	public T get(ID id) {
		T t = getRepository().findById(id).get();
		return t;
	}
	
	public List<T> findAll() {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		Iterable<T> it = getRepository().findAll();
		
		if(it == null){
			return null;
		}
		
		List<T> list = new ArrayList<T>();
		Iterator<T> it2 = it.iterator();
		while(it2.hasNext()){
			list.add(it2.next());
		}

		stopWatch.stop();
		getLog().info(stopWatch.getTime() + "ms");

		return list;
	}
	
	public T findBy(String query, Object... params) {

		T t = getRepository().findBy(query, params);
		return t;
	}

	@Override
	public long countBy(String query, Object... params) {
		return getRepository().countBy(query, params);
	}
	
	public List<T> findAllBy(String query, Object... params) {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		List<T> list = getRepository().findAllBy(query, params);

		stopWatch.stop();
		getLog().info(stopWatch.getTime() + "ms");

		return list;
	}

	public List<T> findByQueryInfo(JSONObject queryInfo) {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		List<T> list = getRepository().findByQueryInfo(queryInfo);

		stopWatch.stop();
		getLog().info(stopWatch.getTime() + "ms");

		return list;

	}

	public long countByQueryInfo(JSONObject queryInfo) {

		return getRepository().countByQueryInfo(queryInfo);

	}

}
