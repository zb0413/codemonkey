package com.codemonkey.mybatis.dao;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("mybatisDao")
public class MybatisDao extends AbsMybatisDao{
	
	@Autowired 
	@Resource(name="sqlSessionFactory")
	private SqlSessionFactory sqlSessionFactory;

	@Override
	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}
	
}
