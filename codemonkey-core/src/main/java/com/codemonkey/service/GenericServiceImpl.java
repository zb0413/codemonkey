package com.codemonkey.service;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import com.codemonkey.domain.IEntity;
import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.ValidationError;
import com.codemonkey.support.CodeMonkeyRepository;
import com.codemonkey.utils.SysUtils;

@Transactional
public abstract class GenericServiceImpl<T extends IEntity<ID> , ID extends Serializable> extends AbsService
		implements GenericService<T , ID> {

	@Override
	public abstract CodeMonkeyRepository<T, ID> getRepository();

	
	public void save(List<T> list){
		if(SysUtils.isNotEmpty(list)){
			for(T t : list){
				save(t);
			}
		}
	}
	
	public void save(T entity) {

		Set<FieldValidation> set = validate(entity);
		if (CollectionUtils.isNotEmpty(set)) {
			throw new ValidationError(set);
		}

		getRepository().save(entity);
	}
	
	protected Set<FieldValidation> validate(T entity) {
		Set<FieldValidation> errorSet = new HashSet<FieldValidation>();
		return errorSet;
	}

	
	public boolean isUnique(T t, String query, Object... params) {

		return getRepository().isUnique(t, query, params);
		
	}

	protected Set<FieldValidation> validateJson(JSONObject body) {
		return new HashSet<FieldValidation>();
	}

	@Override
	public void delete(T t) {
		
		if(t == null){
			return;
		}
		
		if(t.isNew()){
			return;
		}
		
		delete(t);
	}
	
	protected void addValidation(Set<FieldValidation> set , FieldValidation validation){
		
		if(set == null || validation == null){
			return;
		}
		
		set.add(validation);
		
	}
	
	@Override
	public void delete(List<T> list) {
		
		if(SysUtils.isNotEmpty(list)){
			for( T t : list){
				delete(t);
			}
		}
	}

}
