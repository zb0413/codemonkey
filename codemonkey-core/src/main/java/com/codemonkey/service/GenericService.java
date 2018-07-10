package com.codemonkey.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

import com.codemonkey.domain.IEntity;

public interface GenericService<T> extends Converter<String, T> {

	T get(Long id);

    void save(T entity);
    
    void save(List<T> entity);
    
    void saveEntity(IEntity entity);

    void doDelete(Long id);
    
    void doBatchedDelete(List<T> list);
    
    void delete(Long id);
    
    void delete(T entity);
    
    long count();
    
    List<T> findAll();
    
    long countBy(String query , Object... params);
    
    double sumBy(String field , String query , Object... params);
    
    T findBy(String query , Object... params);
    
	List<T> findAllBy(String query , Object... params);
	
	List<T> findByQueryInfo(JSONObject queryAndSort, Integer start, Integer limit);
	
	List<T> findByQueryInfo(JSONObject queryAndSort);

	long countByQueryInfo(JSONObject queryAndSort);

	T doSave(JSONObject body , FormattingConversionServiceFactoryBean ccService);
	
	void doBatchedSave(JSONObject body, FormattingConversionServiceFactoryBean ccService);
	
	T buildEntity(JSONObject params , FormattingConversionServiceFactoryBean ccService);
	
	T createEntity();
	
	boolean isUnique(T t , String query , Object... params);
	
	void doDelete(List<Long> list);
	
	List<T> list(String hql ,  Object... params);
	
	List<T> listPage(String hql , Integer start, Integer limit , Object... params);
	
	Object uniqueResult(String hql , Object... params);

}
