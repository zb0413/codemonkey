package com.codemonkey.service;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.time.StopWatch;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import com.codemonkey.domain.IEntity;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.HqlHelper;
import com.codemonkey.utils.SysUtils;

@Transactional
public abstract class LogicalServiceImpl<T extends IEntity<ID> , ID extends Serializable> extends GenericServiceImpl<T , ID> {

	public long count(){
		return getRepository().countBy("delFlg", false);
	}
	
	public T get(Long id) {
		T t = getRepository().findBy("id&&delFlg", id , false);
		return t;
	}
	
	public List<T> findAll() {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		List<T> list =  getRepository().findAllBy("delFlg", false);

		stopWatch.stop();
		getLog().debug(stopWatch.getTime() + "ms");

		return list;
	}
	
	public T findBy(String query, Object... params) {

		Object[] params2 = SysUtils.appendArray(params, false);

		T t = getRepository().findBy(query + "&&delFlg" , params2);

		return t;
	}

	@Override
	public long countBy(String query, Object... params) {
		
		Object[] params2 = SysUtils.appendArray(params, false);
		
		return getRepository().countBy(query + "&&delFlg", params2);
	}
	

	public List<T> findAllBy(String query, Object... params) {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		Object[] params2 = SysUtils.appendArray(params, false);
		
		if(query.indexOf(HqlHelper.ORDER_BY) >= 0){
			String queryBy = query.substring(0 , query.indexOf(HqlHelper.ORDER_BY));
			String orderBy = query.substring(query.indexOf(HqlHelper.ORDER_BY));
			
			if(SysUtils.isNotEmpty(queryBy)){
				query = queryBy + "&&delFlg";
			}else{
				query = "delFlg";
			}
			query += orderBy;
		}else{
			if(SysUtils.isNotEmpty(query)){
				query += "&&delFlg";
			}else{
				query += "delFlg";
			}
		}
		List<T> list = getRepository().findAllBy( query , params2);

		stopWatch.stop();
		getLog().debug(stopWatch.getTime() + "ms");

		return list;
	}

	public List<T> findByQueryInfo(JSONObject queryAndSort) {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		queryAndSort.getJSONObject(ExtConstant.QUERY).put("delFlg", false);
		
		List<T> list = getRepository().findByQueryInfo(queryAndSort);

		stopWatch.stop();
		getLog().debug(stopWatch.getTime() + "ms");

		return list;

	}

	public long countByQueryInfo(JSONObject queryAndSort) {

		queryAndSort.getJSONObject(ExtConstant.QUERY).put("delFlg", false);
		return getRepository().countByQueryInfo(queryAndSort);

	}
	
	@Override
	public void delete(T t) {
		t.setDelFlg(true);
		save(t);
	}

}
