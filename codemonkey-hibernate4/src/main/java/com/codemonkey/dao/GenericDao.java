package com.codemonkey.dao;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.codemonkey.domain.IEntity;
import com.codemonkey.error.SysError;
import com.codemonkey.utils.ClassHelper;
import com.codemonkey.utils.HqlHelper;
import com.codemonkey.utils.SysUtils;
import com.codemonkey.xqlBuilder.XqlBuilder;
import com.codemonkey.xqlBuilder.XqlResult;

@SuppressWarnings("unchecked")
public class GenericDao<T extends IEntity> {

	private Class<?> type;
	
	private Logger logger = SysUtils.getLog(getClass());
    
	private EntityManager entityManager;
	
	public GenericDao(EntityManager entityManager){
		this.type = ClassHelper.getSuperClassGenricType(getClass());
		this.entityManager = entityManager;
	}
	
	public GenericDao(Class<?> type , EntityManager entityManager){
		this.type = type;
		this.entityManager = entityManager;
	}
	
	public T get(Long id){
    	return (T) entityManager.find(getType(), id , LockModeType.OPTIMISTIC);
    }
	
	public void saveAndFlush(T t){
		save(t);
		entityManager.flush();
	}

    public void save(T t){
    	
    	if(t.getId() == null){
    		t.setCreationDate(new Date());
    		t.setCreatedUser(SysUtils.getCurrentUser());
    		
    		entityManager.persist(t);
    		
    	}else{
    		t.setModificationDate(new Date());
    		t.setModifiedUser(SysUtils.getCurrentUser());
    		
    		entityManager.merge(t);
    		
    	}
		
    }
    
    public void saveEntity(IEntity t) {
    	if(t.getId() == null){
    		t.setCreationDate(new Date());
    		t.setCreatedUser(SysUtils.getCurrentUser());
    		entityManager.persist(t);
    	}else{
    		t.setModificationDate(new Date());
    		t.setModifiedUser(SysUtils.getCurrentUser());
    		entityManager.merge(t);
    	}
	}
    
    public void deleteAndFlush(Long id){
    	delete(id);
    	entityManager.flush();
    }

    public void delete(Long id){
    	
    	if(id == null){
    		return;
    	}
    	
    	T t = get(id);
    	
    	if(t == null){
    		return;
    	}
    	
    	entityManager.remove(t);
    }
    
    public List<T> findAll(){
    	return findAllBy("");
    }
    
    public List<T> findAll(int start, int limit) {
    	String hql = HqlHelper.findBy(getType() , "");
		
		Query hqlQuery = builHqlQuery(hql);
		hqlQuery.setFirstResult(start).setMaxResults(limit);
		return (List<T>) toListResult(hqlQuery);
	}

    
	public List<T> findAllBy(String query, Object... params) {
		
		checkparams(params);
		
		String hql = HqlHelper.findBy(getType(), query);
		
		Query hqlQuery = builHqlQuery(hql, params);
		
		return (List<T>) toListResult(hqlQuery);
	}

	
	
	public T findBy(String query, Object... params) {
		
		checkparams(params);
		String hql = HqlHelper.findBy(getType(), query , params);
		Query hqlQuery = builHqlQuery(hql, params);
		return (T)toUniqueResult(hqlQuery);
	
	}

	private Object toUniqueResult(Query hqlQuery) {
		try{
			return hqlQuery.getSingleResult();
		}catch(NoResultException e){
			//e.printStackTrace();
		}
		return null;
	}
	
	private Object toListResult(Query hqlQuery) {
		try{
			return hqlQuery.getResultList();
		}catch(NoResultException e){
			//e.printStackTrace();
		}
		return null;
	}
	
	public long countBy(String query, Object... params) {
		checkparams(params);
		String hql = HqlHelper.countBy(getType(), query , params);
		Query hqlQuery = builHqlQuery(hql, params);
		return countResult((Long) toUniqueResult(hqlQuery));
	}
	
	public double sumBy(String field, String query, Object[] params) {
		
		checkparams(params);
		String hql = HqlHelper.sumBy(getType(), query , field);
		Query hqlQuery = builHqlQuery(hql, params);
		Object result = toUniqueResult(hqlQuery);
		
		if(result != null){
			if(result instanceof Double){
				return (Double)result;
			}else if(result instanceof Long){
				BigDecimal b = new BigDecimal((Long)result);
				return b.doubleValue();
			}else if(result instanceof Integer){
				BigDecimal b = new BigDecimal((Integer)result);
				return b.doubleValue();
			}else if(result instanceof Short){
				BigDecimal b = new BigDecimal((Short)result);
				return b.doubleValue();
			}else if(result instanceof Float){
				BigDecimal b = new BigDecimal((Float)result);
				return b.doubleValue();
			}else{
				throw new SysError("未知类型无法转换成double："+result.getClass());
			}
		}
		return 0;
	}
	
	private Query builHqlQuery(String hql, Object... params) {
		
		logger.debug(hql);
		checkparams(params);
		Query hqlQuery = entityManager.createQuery(hql);
		int current = 0;
		if(params != null && params.length > 0) {
			for(int i = 0 ; i < params.length ; i++){
				if(params[i] instanceof Collection){
					Collection<?> c = (Collection<?>) params[i];
					for(Object obj : c){
						hqlQuery.setParameter(current, obj);
						current++;
					}
				}else{
					hqlQuery.setParameter(current, params[i]);
					current++;
				}
			}
		}
		return hqlQuery;
	}
	
	public List<T> findByQueryInfo(JSONObject queryAndSort, Integer start, Integer limit) {
		
		String hql = HqlHelper.findByQueryInfo(getType(), queryAndSort);
		
		List<Object> params = HqlHelper.extractParamsFromQueryInfo(getType(), queryAndSort);
		Query hqlQuery = builHqlQuery(hql , params.toArray());
		hqlQuery.setMaxResults(limit).setFirstResult(start);
		return (List<T>) toListResult(hqlQuery);
	}
	
	public List<Object> findByQueryInfo(String hqlSelect , JSONObject queryAndSort, Integer start, Integer limit) {
		String hql = HqlHelper.findByQueryInfo(getType() , queryAndSort);
		hql = hql.replace(HqlHelper.SELECT_FROM , hqlSelect);
		List<Object> params = HqlHelper.extractParamsFromQueryInfo(getType(), queryAndSort);
		Query hqlQuery = builHqlQuery(hql , params.toArray());
		hqlQuery.setMaxResults(limit).setFirstResult(start);
		return (List<Object>) toListResult(hqlQuery);
	}
	
	public List<T> findByQueryInfo(JSONObject queryAndSort) {
		String hql = HqlHelper.findByQueryInfo(getType(), queryAndSort);
		List<Object> params = HqlHelper.extractParamsFromQueryInfo(getType(), queryAndSort);
		Query hqlQuery = builHqlQuery(hql , params.toArray());
		return (List<T>) toListResult(hqlQuery);
	}
	
	public long countByQueryInfo(JSONObject queryAndSort) {
		String hql = HqlHelper.countByQueryInfo(getType(), queryAndSort);
		List<Object> params = HqlHelper.extractParamsFromQueryInfo(getType(), queryAndSort);
		Query hqlQuery = builHqlQuery(hql , params.toArray());
		return countResult((Long)toUniqueResult(hqlQuery));
	}
	
	public long countByQueryInfo(String hqlSelect , JSONObject queryAndSort) {
		String hql = HqlHelper.countByQueryInfo(getType() , queryAndSort);
		if(SysUtils.isNotEmpty(hqlSelect)){
			hql = hql.replace(HqlHelper.SELECT_COUNT_FROM , hqlSelect);
		}
		List<Object> params = HqlHelper.extractParamsFromQueryInfo(getType(), queryAndSort);
		Query hqlQuery = builHqlQuery(hql , params.toArray());
		return countResult((Long) toUniqueResult(hqlQuery));
	}
	
	private long countResult(Long count){
		if(count == null){
			return 0;
		}
		return count;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public List<T> list(String hql, Object... params) {
		checkparams(params);
		Query hqlQuery = builHqlQuery(hql , params);
		return (List<T>) toListResult(hqlQuery);
	}

	public Object uniqueResult(String hql, Object... params) {
		checkparams(params);
		Query hqlQuery = builHqlQuery(hql , params);
		return toUniqueResult(hqlQuery);
	}

	public List<T> listPage(String hql, Integer start, Integer limit , Object... params) {
		checkparams(params);
		Query hqlQuery = builHqlQuery(hql , params);
		hqlQuery.setMaxResults(limit).setFirstResult(start);
		return (List<T>) toListResult(hqlQuery);
	}

	public T uniqueResultByXql(String xql, Object params) {
		XqlResult xqlResult =  XqlBuilder.build(xql, params);
		
		Query hqlQuery = buildQuery(xqlResult);
		
		return (T)toUniqueResult(hqlQuery);
	}

	private Query buildQuery(XqlResult xqlResult) {
		
		String hql = xqlResult.getXql();
		
		logger.debug(hql);
		
		Query hqlQuery = entityManager.createQuery(hql);
		
		List<Object> params = xqlResult.getParams();
		
		if(SysUtils.isNotEmpty(params)) {
			for(int i = 0 ; i < params.size() ; i++){
				hqlQuery.setParameter(i, params.get(i));
			}
		}
		return hqlQuery;
	}

	//TODO fix order by
	public List<T> listByXql(String xql, Object params, Integer start, Integer limit , Object sorts) {
		
		XqlResult xqlResult =  XqlBuilder.build(xql, params);
		
		Query hqlQuery = buildQuery(xqlResult);
		
		if(start != null){
			hqlQuery.setFirstResult(start);
		}
		
		if(limit != null){
			hqlQuery.setMaxResults(limit);
		}
		
		return (List<T>) toListResult(hqlQuery);
	}

	public long countByXql(String xql, Object params) {
		XqlResult xqlResult =  XqlBuilder.build(xql, params);
		
		Query hqlQuery = buildQuery(xqlResult);
		
		Long count = (Long) toUniqueResult(hqlQuery);
		
		if(count != null){
			return count;
		}
		return 0;
	}
	
	private void checkparams(Object... params) {
		if(params != null && params.length > 0) {
			for(int i = 0 ; i < params.length ; i++){
				if(params[i] == null){
					throw new SysError("数据异常！");
				}
			}
		}
	}

	public long count() {
		return countBy("");
	}
	
}
