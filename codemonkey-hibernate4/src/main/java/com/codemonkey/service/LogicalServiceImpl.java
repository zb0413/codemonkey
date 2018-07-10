package com.codemonkey.service;

import java.util.List;

import org.apache.commons.lang.time.StopWatch;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import com.codemonkey.domain.IEntity;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.HqlHelper;
import com.codemonkey.utils.SysUtils;

@Transactional
public abstract class LogicalServiceImpl<T extends IEntity> extends GenericServiceImpl<T> {

	public long count(){
		return getDao().countBy("delFlg", false);
	}
	
	public T get(Long id) {
		T t = getDao().findBy("id&&delFlg", id , false);
		return t;
	}
	
	public List<T> findAll() {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		List<T> list =  getDao().findAllBy("delFlg", false);

		stopWatch.stop();
		getLog().debug(stopWatch.getTime() + "ms");

		return list;
	}
	
	public T findBy(String query, Object... params) {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		Object[] params2 = SysUtils.appendArray(params, false);

		T t = getDao().findBy(query + "&&delFlg" , params2);

		stopWatch.stop();
		getLog().debug(stopWatch.getTime() + "ms");

		return t;
	}

	@Override
	public long countBy(String query, Object... params) {
		
		Object[] params2 = SysUtils.appendArray(params, false);
		
		return getDao().countBy(query + "&&delFlg", params2);
	}
	
	@Override
	public double sumBy(String field , String query, Object... params) {
		
		Object[] params2 = SysUtils.appendArray(params, false);
		
		return getDao().sumBy(field , query + "&&delFlg" , params2);
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
		List<T> list = getDao().findAllBy( query , params2);

		stopWatch.stop();
		getLog().debug(stopWatch.getTime() + "ms");

		return list;
	}

	public List<T> findByQueryInfo(JSONObject queryAndSort, Integer start,
			Integer limit) {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		queryAndSort.getJSONObject(ExtConstant.QUERY).put("delFlg", false);

		List<T> list = getDao().findByQueryInfo(queryAndSort, start, limit);

		stopWatch.stop();
		getLog().debug(stopWatch);

		return list;

	}
	
	public List<Object> findByQueryInfo(String replaceHql , JSONObject queryAndSort,
			Integer start, Integer limit) {
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		queryAndSort.getJSONObject(ExtConstant.QUERY).put("delFlg", false);

		List<Object> list = getDao().findByQueryInfo(replaceHql , queryAndSort, start, limit);

		stopWatch.stop();
		getLog().debug(stopWatch.getTime() + "ms");

		return list;
		
	}

	public List<T> findByQueryInfo(JSONObject queryAndSort) {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		queryAndSort.getJSONObject(ExtConstant.QUERY).put("delFlg", false);
		
		List<T> list = getDao().findByQueryInfo(queryAndSort);

		stopWatch.stop();
		getLog().debug(stopWatch.getTime() + "ms");

		return list;

	}

	public long countByQueryInfo(JSONObject queryAndSort) {

		return countByQueryInfo(queryAndSort , null);

	}
	
	public long countByQueryInfo(JSONObject queryAndSort, String replaceHql) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		queryAndSort.getJSONObject(ExtConstant.QUERY).put("delFlg", false);
		
		long count = getDao().countByQueryInfo(replaceHql , queryAndSort);

		stopWatch.stop();
		getLog().debug(stopWatch.getTime() + "ms");

		return count;
	}
	
	@Override
	public void delete(Long id) {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		T t = getDao().get(id);

		t.setDelFlg(true);
		getDao().save(t);
		//this.save(t);

		stopWatch.stop();
		getLog().debug(stopWatch.getTime() + "ms");

	}

}
