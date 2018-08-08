package com.codemonkey.repository.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QuerydslJpaRepository;

import com.codemonkey.error.SysError;
import com.codemonkey.repository.helper.QueryDslHelper;
import com.codemonkey.support.CodeMonkeyRepository;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.OgnlUtils;
import com.codemonkey.utils.SysUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;

public abstract class AbsCodeMonkeyRepositoryImpl<T , ID extends Serializable> extends QuerydslJpaRepository<T, ID> implements CodeMonkeyRepository<T , ID>{

//	private Logger logger = SysUtils.getLog(getClass());
	
	private JpaEntityInformation<T, ?> entityInformation;
	
	private EntityManager entityManager;
	
	public AbsCodeMonkeyRepositoryImpl( JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityInformation = entityInformation;
		this.entityManager = entityManager;
	}
	
	@Override
	public List<T> findAllBy(String query, Object... params) {
		QueryDslHelper helper = new QueryDslHelper();
		JSONObject queryAndSort = helper.toQueryAndSort(query , params);
		return findByQueryInfo(queryAndSort);
	}
	
	@Override
	public T findBy(String query, Object... params) {
		
		QueryDslHelper helper = new QueryDslHelper();
		JSONObject queryAndSort = helper.toQueryAndSort(query , params);
		
		List<T> list = findByQueryInfo(queryAndSort);
		if(SysUtils.isEmpty(list)){
			return null;
		}
		if(list.size() > 2){
			throw new SysError("返回结果不唯一");
		}
		return list.get(0);
	
	}
	
	@Override
	public long countBy(String query, Object... params) {
		QueryDslHelper helper = new QueryDslHelper();
		JSONObject queryAndSort = helper.toQueryAndSort(query , params);
		
		return countByQueryInfo(queryAndSort);
	}
	
	@Override
	public Page<T> findByQueryInfo(JSONObject queryAndSort , Pageable pageable) {
		
		QueryDslHelper helper = new QueryDslHelper();
		Predicate p = helper.createPredicate(getType() , queryAndSort.optJSONObject(ExtConstant.QUERY));
		
		List<OrderSpecifier<?>> orders = helper.createOrders(getType() , queryAndSort.optJSONArray(ExtConstant.SORT));
		
		Sort sort = pageable.getSort();
		List<Order> pageOrders = toPageOrders(orders);
		Sort otherSort = Sort.by(pageOrders);
		sort.and(otherSort);
		return findAll(p , pageable);
	}
	
	private List<Order> toPageOrders(List<OrderSpecifier<?>> orders) {
		List<Order> pageOrders = new ArrayList<Order>();
		
		if(SysUtils.isNotEmpty(orders)){
			for(OrderSpecifier<?> o : orders){
				if(o.getOrder().ASC.equals(o.getOrder())){
					Order order = new Order(Direction.ASC , o.getTarget().toString());
					pageOrders.add(order);
				}else{
					Order order = new Order(Direction.DESC , o.getTarget().toString());
					pageOrders.add(order);
				}
			}
		}
		return pageOrders;
	}

	@Override
	public List<T> findByQueryInfo(JSONObject queryAndSort) {
		QueryDslHelper helper = new QueryDslHelper();
		Predicate p = helper.createPredicate(getType() , queryAndSort.optJSONObject(ExtConstant.QUERY));
		List<OrderSpecifier<?>> orders = helper.createOrders(getType()  , queryAndSort.optJSONArray(ExtConstant.SORT));

		if(SysUtils.isNotEmpty(orders)){
			OrderSpecifier<?>[] orderArray = new OrderSpecifier<?>[orders.size()];
			orderArray = orders.toArray(orderArray);
			return findAll(p , orderArray);
		}
		
		return findAll(p);
	}
	
	@Override
	public long countByQueryInfo(JSONObject queryAndSort) {
		QueryDslHelper helper = new QueryDslHelper();
		Predicate p = helper.createPredicate(getType() , queryAndSort.optJSONObject(ExtConstant.QUERY));
		return count(p);
	}
	
	@Override
	public boolean isUnique(T t, String query, Object... params) {

		long count = countBy(query, params);

		String id = OgnlUtils.stringValue("id", t);
		
		if (SysUtils.isEmpty(id)) {
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
	
	protected Class<?> getType() {
		return entityInformation.getJavaType();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
}
