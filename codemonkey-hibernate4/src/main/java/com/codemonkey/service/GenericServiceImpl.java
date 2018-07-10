package com.codemonkey.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.transaction.annotation.Transactional;

import com.codemonkey.dao.GenericDao;
import com.codemonkey.domain.IEntity;
import com.codemonkey.error.BadObjVersionError;
import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.FormFieldValidation;
import com.codemonkey.error.ValidationError;
import com.codemonkey.utils.ClassHelper;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.SysUtils;

@Transactional
public abstract class GenericServiceImpl<T extends IEntity> extends AbsService
		implements GenericService<T> {

	private GenericDao<T> dao;

	private Class<?> type;
	
	private EntityManager entityManager;
	
	@Override
	public abstract T createEntity();

	@Autowired
	public void setEntityManager(EntityManager entityManager) {
		this.type = ClassHelper.getSuperClassGenricType(getClass());
		dao = new GenericDao<T>(getType() , entityManager);
	}
	
	EntityManager getEntityManager(){
		return this.entityManager;
	}

	protected GenericDao<T> getDao() {
		return dao;
	}
	
	public T uniqueResultByXql(String xql ,  Object params){
		return dao.uniqueResultByXql(xql , params);
	}
	
	public long countByXql(String xql , Object params){
		return dao.countByXql(xql , params);
	}
	
	public List<T> listByXql(String xql ,  Object params){
		return dao.listByXql(xql , params , null , null , null);
	}
	
	public List<T> listByXql(String xql ,  Object params , Object sorts){
		return dao.listByXql(xql , params , null , null , sorts);
	}
	
	public List<T> listByXql(String xql , Object params , Integer start, Integer limit){
		return dao.listByXql(xql , params , start , limit , null);
	}
	
	public List<T> listByXql(String xql , Object params , Integer start, Integer limit , Object sorts){
		return dao.listByXql(xql , params , start , limit , sorts);
	}
	
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

		try{
			getDao().save(entity);
		}catch(javax.persistence.OptimisticLockException e){
			throw new BadObjVersionError(entity.listJson());
		}
	}
	
	public void saveEntity(IEntity entity){
		try{
			getDao().saveEntity(entity);
		}catch(javax.persistence.OptimisticLockException e){
			throw new BadObjVersionError(entity.listJson());
		}
	}

	// implements by subclass if needed
	// if validation failed , throw ValidationError exception
	protected Set<FieldValidation> validate(T entity) {
		Set<FieldValidation> errorSet = new HashSet<FieldValidation>();
		if (entity == null) {
			return errorSet;
		}

		if (StringUtils.isNotBlank(entity.getCode())) {
			if (!isUnique(entity, "code", entity.getCode())) {
				errorSet.add(new FormFieldValidation("code",  "编码不能重复，请保证编码的唯一性"));
			}
		}

		return errorSet;
	}

	public T convert(String source) {

		if (StringUtils.isEmpty(source)) {
			return null;
		}

		return getDao().get(Long.valueOf(source));
	}

	@Override
	public void doDelete(List<Long> list) {
		if(CollectionUtils.isNotEmpty(list)){
			for(Long id : list){
				delete(id);
			}
		}
	}

	public T doSave(JSONObject body, FormattingConversionServiceFactoryBean ccService) {
		validateInput(body);
		T t = buildEntity(body, ccService);
		save(t);
		return t;
	}
	
	public void doBatchedSave(JSONObject body, FormattingConversionServiceFactoryBean ccService){
		
		JSONArray recordsToModify = body.optJSONArray(ExtConstant.RECORDS_TO_MODIFY);
		
		JSONArray recordsToDelete = body.optJSONArray(ExtConstant.RECORDS_TO_DELETE);
		
		if(recordsToDelete != null){
			for(int i = 0 ; i < recordsToDelete.length() ; i++){
				JSONObject params = recordsToDelete.getJSONObject(i);
				
				Long id = params.getLong(ExtConstant.ID);
				if(id != null){
					delete(id);
				}
			}
		}
		
		if(recordsToModify != null){
			for(int i = 0 ; i < recordsToModify.length() ; i++){
				JSONObject params = recordsToModify.getJSONObject(i);
				doSave(params, ccService);
			}
		}
	}

	public boolean isUnique(T t, String query, Object... params) {

		long count = countBy(query, params);

		if (t.isNew()) {
			if (count > 0) {
				return false;
			}
		} else {
			if (count > 1) {
				return false;
			}
		}
		return true;
	}

	protected void validateInput(JSONObject body) {
		Set<FieldValidation> errorSet = validateJson(body);
		errorSet.remove(null);
		if (CollectionUtils.isNotEmpty(errorSet)) {
			throw new ValidationError(errorSet);
		}
	}

	protected Set<FieldValidation> validateJson(JSONObject body) {
		return new HashSet<FieldValidation>();
	}

	public T buildEntity(JSONObject params, FormattingConversionServiceFactoryBean ccService) {
		T t = null;
		Long id = extractId(params);

		if (id == null) {
			t = createEntity();
			ClassHelper.build(params, t, ccService);
		} else {
			t = get(id);
			if (t != null) {
				ClassHelper.build(params, t, ccService);
			}
		}
		return t;
	}
	
	@Override
	public void doDelete(Long id) {
		delete(id);
	}
	
	@Override
	public void doBatchedDelete(List<T> list){
		if(SysUtils.isNotEmpty(list)){
			for(T t : list){
				delete(t);
			}
		}
	}
	
	@Override
	public void delete(T t) {
		
		if(t == null){
			return;
		}
		
		if(t.isNew()){
			return;
		}
		delete(t.getId());
	}
	
	protected void addValidation(Set<FieldValidation> set , FieldValidation validation){
		
		if(set == null || validation == null){
			return;
		}
		
		set.add(validation);
		
	}

	public Long extractId(JSONObject params) {
		Long id = null;
		if (params.has(ExtConstant.ID)
				&& StringUtils.isNotBlank(params.getString(ExtConstant.ID))
				&& !"null".equals(params.getString(ExtConstant.ID))) {
			id = params.getLong(ExtConstant.ID);
		}
		return id;
	}
	
	public List<T> list(String hql ,  Object... params){
		return dao.list(hql , params);
	}
	
	public List<T> listPage(String hql , Integer start, Integer limit , Object... params){
		return dao.listPage(hql , start , limit , params);
	}
	
	public Object uniqueResult(String hql ,  Object... params){
		return dao.uniqueResult(hql , params);
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

}
