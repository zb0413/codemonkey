package com.codemonkey.mybatis.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemonkey.mybatis.dao.MybatisDao;

@Service("mybatisService")
public class MybatisServiceImpl extends AbsMybatisServiceImpl {

	@Autowired 
	@Resource(name="mybatisDao")
	private MybatisDao dao;

	@Override
	public MybatisDao getDao() {
		return dao;
	}
	
}
