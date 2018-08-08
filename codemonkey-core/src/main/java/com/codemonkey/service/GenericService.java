package com.codemonkey.service;

import java.io.Serializable;
import java.util.List;

import org.json.JSONObject;
import org.springframework.data.repository.Repository;

import com.codemonkey.domain.IEntity;

public interface GenericService<T extends IEntity<ID> , ID extends Serializable> {

	Repository<T, ID> getRepository();
	
	T get(ID id);

    void save(T entity);
    
    void save(List<T> entity);
    
    void delete(List<T> list);
    
    void delete(T entity);
    
    long count();
    
    List<T> findAll();
    
    long countBy(String query , Object... params);
    
    T findBy(String query , Object... params);
    
	List<T> findAllBy(String query , Object... params);
	
	List<T> findByQueryInfo(JSONObject queryAndSort);

	long countByQueryInfo(JSONObject queryAndSort);

	T createEntity();
	
	boolean isUnique(T t , String query , Object... params);
	
}
