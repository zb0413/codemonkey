package com.codemonkey.repository.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.json.JSONObject;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;

import com.codemonkey.domain.QGlobalConfig;
import com.codemonkey.repository.helper.QueryDslHelper;
import com.codemonkey.support.CodeMonkeyRepository;
import com.codemonkey.utils.ClassHelper;
import com.codemonkey.utils.ExtConstant;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class JpaCodeMonkeyRepositoryImpl<T , ID extends Serializable> extends AbsCodeMonkeyRepositoryImpl<T, ID> implements CodeMonkeyRepository<T , ID>{

	public JpaCodeMonkeyRepositoryImpl(
			JpaEntityInformation<T, ID> entityInformation,
			EntityManager entityManager) {
		super(entityInformation, entityManager);
	}

	@Override
	public double sumBy(String field, String query, Object... params) {
		EntityPathResolver resolver = SimpleEntityPathResolver.INSTANCE;
		
		EntityPath<?> root = resolver.createPath(getType());
		
		QueryDslHelper helper = new QueryDslHelper();
		
		SimpleExpression<?> path = helper.toStringPath(root, field);
		
		JSONObject queryAndSort = helper.toQueryAndSort(query , params);
		Predicate predicate = helper.createPredicate(getType(), queryAndSort.optJSONObject(ExtConstant.QUERY));
		JPAQueryFactory queryFatory = new JPAQueryFactory(getEntityManager());
		
		QGlobalConfig.globalConfig.version.sum();
		Double result = (Double) queryFatory.select(
            Projections.bean(
        		Double.class,//返回自定义实体的类型
        		(Expression<?>)ClassHelper.callMethod(path, "sum")   
            )
        ).from(root).where(predicate).fetchOne();
		if(result == null){
			return 0d;
		}
		return result;
	}
}
