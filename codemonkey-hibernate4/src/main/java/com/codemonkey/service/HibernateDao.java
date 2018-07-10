package com.codemonkey.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.json.JSONObject;

import com.codemonkey.domain.IEntity;
import com.codemonkey.error.SysError;
import com.codemonkey.utils.ClassHelper;
import com.codemonkey.utils.HqlHelper;
import com.codemonkey.utils.SysUtils;
import com.codemonkey.xqlBuilder.XqlBuilder;
import com.codemonkey.xqlBuilder.XqlResult;

@SuppressWarnings("unchecked")
public class HibernateDao<T extends IEntity> {

	private Class<?> type;
	
	private Logger logger = SysUtils.getLog(getClass());
    
	private SessionFactory sessionFactory;
	
	public HibernateDao(SessionFactory sessionFactory){
		this.type = ClassHelper.getSuperClassGenricType(getClass());
		this.sessionFactory = sessionFactory;
	}
	
	public HibernateDao(Class<?> type , SessionFactory sessionFactory){
		this.type = type;
		this.sessionFactory = sessionFactory;
	}
	
    public Session getSession(){
    	return sessionFactory.getCurrentSession();
    }
    
	public T get(Long id){
    	return (T) getSession().get(getType(), id);
    }
	
	public void saveAndFlush(T t){
		save(t);
	    getSession().flush();
	}

    public void save(T t){
    	
    	if(t.getId() == null){
    		t.setCreationDate(new Date());
    		t.setCreatedUser(SysUtils.getCurrentUser());
    		
    		getSession().saveOrUpdate(t);
    		
    	}else{
    		t.setModificationDate(new Date());
    		t.setModifiedUser(SysUtils.getCurrentUser());
    		
    		getSession().merge(t);
    		
    	}
		
    }
    
    public void saveEntity(IEntity t) {
    	if(t.getId() == null){
    		t.setCreationDate(new Date());
    		t.setCreatedUser(SysUtils.getCurrentUser());
    	}else{
    		t.setModificationDate(new Date());
    		t.setModifiedUser(SysUtils.getCurrentUser());
    	}
		getSession().saveOrUpdate(t);
	}
    
    public void deleteAndFlush(Long id){
    	delete(id);
    	getSession().flush();
    }

    public void delete(Long id){
    	
    	if(id == null){
    		return;
    	}
    	
    	T t = get(id);
    	
    	if(t == null){
    		return;
    	}
    	
    	getSession().delete(t);
    }
    
    public List<T> findAll(){
    	return buildCriteria().list();
    }
    
    public List<T> findAll(int start, int limit) {
    	Criteria c = buildCriteria();
    	c.setFirstResult(start);
    	c.setMaxResults(limit);
    	return c.list();
	}

	private Criteria buildCriteria() {
		return getSession().createCriteria(getType());
	}
    
    private Criteria buildCriteria(List<Criterion> criterions) {
		return buildCriteria(criterions , null);
	}

	private Criteria buildCriteria(List<Criterion> criterions , List<Order> orders) {
		Criteria c = buildCriteria();
    	
    	if(criterions != null && !criterions.isEmpty()){
    		for(Criterion cr : criterions){
    			c.add(cr);
    		}
    	}
    	
    	if(orders != null && !orders.isEmpty()){
    		for(Order order : orders){
    			c.addOrder(order);
    		}
    	}
		return c;
	}
	
	public List<T> findByCriteria(Criterion... criterions){
		List<Criterion> conditions = new ArrayList<Criterion>();
		if(criterions != null){
			conditions = Arrays.asList(criterions);
		}
    	return findByCriteria(conditions);
    }
	
	public List<T> findByCriteria(int start, int limit, Criterion... criterions){
    	return findByCriteria(start , limit , Arrays.asList(criterions));
    }
	
	public List<T> findByCriteria(List<Criterion> criterions){
    	Criteria c = buildCriteria(criterions);
    	return c.list();
    }
	
	public List<T> findByCriteria(int start, int limit, List<Criterion> criterions){
    	Criteria c = buildCriteria(criterions);
    	c.setFirstResult(start);
    	c.setMaxResults(limit);
    	return c.list();
    }
    
	public long count(Criterion... criterions){
		List<Criterion> conditions = new ArrayList<Criterion>();
		if(criterions != null){
			conditions = Arrays.asList(criterions);
		}
		return count(conditions);
    }
	
	public long count(List<Criterion> criterions){
    	Criteria c = buildCriteria(criterions);
    	c.setProjection(Projections.rowCount());
    	return countResult((Long) c.uniqueResult());
    }
    
	public List<T> findAllBy(String query, Object... params) {
		
		checkparams(params);
		
		String hql = HqlHelper.findBy(getType(), query);
		
		Query hqlQuery = builHqlQuery(hql, params);
		
		return hqlQuery.list();
	}

	
	
	public T findBy(String query, Object... params) {
		
		checkparams(params);
		String hql = HqlHelper.findBy(getType(), query);
		Query hqlQuery = builHqlQuery(hql, params);
		return (T) hqlQuery.uniqueResult();
	
	}
	
	public long countBy(String query, Object... params) {
		checkparams(params);
		String hql = HqlHelper.countBy(getType(), query);
		Query hqlQuery = builHqlQuery(hql, params);
		return countResult((Long) hqlQuery.uniqueResult());
	}
	
	public double sumBy(String field, String query, Object[] params) {
		
		checkparams(params);
		String hql = HqlHelper.sumBy(getType(), query , field);
		Query hqlQuery = builHqlQuery(hql, params);
		Object result = hqlQuery.uniqueResult();
		
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
		Query hqlQuery = getSession().createQuery(hql);
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
		return hqlQuery.setMaxResults(limit).setFirstResult(start).list();
	}
	
	public List<Object> findByQueryInfo(String hqlSelect , JSONObject queryAndSort, Integer start, Integer limit) {
		String hql = HqlHelper.findByQueryInfo(getType() , queryAndSort);
		hql = hql.replace(HqlHelper.SELECT_FROM , hqlSelect);
		List<Object> params = HqlHelper.extractParamsFromQueryInfo(getType(), queryAndSort);
		Query hqlQuery = builHqlQuery(hql , params.toArray());
		return hqlQuery.setMaxResults(limit).setFirstResult(start).list();
	}
	
	public List<T> findByQueryInfo(JSONObject queryAndSort) {
		String hql = HqlHelper.findByQueryInfo(getType(), queryAndSort);
		List<Object> params = HqlHelper.extractParamsFromQueryInfo(getType(), queryAndSort);
		Query hqlQuery = builHqlQuery(hql , params.toArray());
		return hqlQuery.list();
	}
	
	public long countByQueryInfo(JSONObject queryAndSort) {
		String hql = HqlHelper.countByQueryInfo(getType(), queryAndSort);
		List<Object> params = HqlHelper.extractParamsFromQueryInfo(getType(), queryAndSort);
		Query hqlQuery = builHqlQuery(hql , params.toArray());
		return countResult((Long) hqlQuery.uniqueResult());
	}
	
	public long countByQueryInfo(String hqlSelect , JSONObject queryAndSort) {
		String hql = HqlHelper.countByQueryInfo(getType() , queryAndSort);
		if(SysUtils.isNotEmpty(hqlSelect)){
			hql = hql.replace(HqlHelper.SELECT_COUNT_FROM , hqlSelect);
		}
		List<Object> params = HqlHelper.extractParamsFromQueryInfo(getType(), queryAndSort);
		Query hqlQuery = builHqlQuery(hql , params.toArray());
		return countResult((Long) hqlQuery.uniqueResult());
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
		return hqlQuery.list();
	}

	public Object uniqueResult(String hql, Object... params) {
		checkparams(params);
		Query hqlQuery = builHqlQuery(hql , params);
		return hqlQuery.uniqueResult();
	}

	public List<T> listPage(String hql, Integer start, Integer limit , Object... params) {
		checkparams(params);
		Query hqlQuery = builHqlQuery(hql , params);
		return hqlQuery.setMaxResults(limit).setFirstResult(start).list();
	}

	public T uniqueResultByXql(String xql, Object params) {
		XqlResult xqlResult =  XqlBuilder.build(xql, params);
		
		Query hqlQuery = buildQuery(xqlResult);
		
		return (T)hqlQuery.uniqueResult();
	}

	private Query buildQuery(XqlResult xqlResult) {
		
		String hql = xqlResult.getXql();
		
		logger.debug(hql);
		
		Query hqlQuery = getSession().createQuery(hql);
		
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
		
		return (List<T>) hqlQuery.list();
	}

	public long countByXql(String xql, Object params) {
		XqlResult xqlResult =  XqlBuilder.build(xql, params);
		
		Query hqlQuery = buildQuery(xqlResult);
		
		Long count = (Long) hqlQuery.uniqueResult();
		
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
	
}
