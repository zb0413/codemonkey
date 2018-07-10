package com.codemonkey.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.stereotype.Service;

import com.codemonkey.domain.IEntity;

@Service
public class EmptyGenericServiceImpl<T extends IEntity> extends AbsService implements GenericService<T>{

	@Override
	public T convert(String source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(T entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(List<T> entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveEntity(IEntity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doDelete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(T entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<T> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countBy(String query, Object... params) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double sumBy(String field, String query, Object... params) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public T findBy(String query, Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findAllBy(String query, Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findByQueryInfo(JSONObject queryAndSort, Integer start,
			Integer limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findByQueryInfo(JSONObject queryAndSort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countByQueryInfo(JSONObject queryAndSort) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public T doSave(JSONObject body,
			FormattingConversionServiceFactoryBean ccService) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doBatchedSave(JSONObject body,
			FormattingConversionServiceFactoryBean ccService) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T buildEntity(JSONObject params,
			FormattingConversionServiceFactoryBean ccService) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T createEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUnique(T t, String query, Object... params) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void doDelete(List<Long> list) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<T> list(String hql, Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> listPage(String hql, Integer start, Integer limit,
			Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object uniqueResult(String hql, Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doBatchedDelete(List<T> list) {
		// TODO Auto-generated method stub
		
	}


}
