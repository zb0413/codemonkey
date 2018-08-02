package com.codemonkey.support;

import java.io.Serializable;
import java.util.List;

import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CodeMonkeyRepository<T , ID extends Serializable> extends PagingAndSortingRepository<T, ID> , QuerydslPredicateExecutor<T> {

	public  List<T> findAllBy(String query, Object... params);

	public  T findBy(String query, Object... params);

	public  long countBy(String query, Object... params);

	public  double sumBy(String field, String query, Object... params);

	public  Page<T> findByQueryInfo(JSONObject queryAndSort , Pageable pageable);

	public  List<T> findByQueryInfo(JSONObject queryAndSort);

	public  long countByQueryInfo(JSONObject queryAndSort);

	boolean isUnique(T t , String query , Object... params);
	
}
