package com.codemonkey.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.OgnlUtils;

public abstract class AbsMybatisDao {

	public abstract SqlSessionFactory getSqlSessionFactory();
	
	public List<Map<String,Object>> query(String id , Object param){
		SqlSession session = getSqlSessionFactory().openSession();
		return session.selectList(id, param);
	}
	
	public List<Map<String,Object>> query(String id){
		SqlSession session = getSqlSessionFactory().openSession();
		return session.selectList(id);
	}
	
	public Long count(String id , Object param){
		SqlSession session = getSqlSessionFactory().openSession();
		return (Long)session.selectOne(id, param);
	}
	
	public Long count(String id){
		SqlSession session = getSqlSessionFactory().openSession();
		return (Long)session.selectOne(id);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> findOne(String sqlId, Object params) {
		SqlSession session = getSqlSessionFactory().openSession();
		return (Map<String, Object>)session.selectOne(sqlId , params);
	}
	
	public Long insert(String id , Object obj){
		SqlSession session = getSqlSessionFactory().openSession();
		session.insert(id , obj);
		return Long.valueOf(OgnlUtils.stringValue(ExtConstant.ID, obj));
	}
	
	public void update(String id , Object obj){
		SqlSession session = getSqlSessionFactory().openSession();
		session.update(id , obj);
	}
	
	public void delete(String id , Object obj){
		SqlSession session = getSqlSessionFactory().openSession();
		session.delete(id , obj);
	}
	
	public void delete(String id){
		SqlSession session = getSqlSessionFactory().openSession();
		session.delete(id);
	}

}
