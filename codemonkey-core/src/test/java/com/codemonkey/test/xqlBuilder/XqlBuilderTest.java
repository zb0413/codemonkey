package com.codemonkey.test.xqlBuilder;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.codemonkey.xqlBuilder.XqlBuilder;
import com.codemonkey.xqlBuilder.XqlResult;

public class XqlBuilderTest {

	@Test
	public void testSplitConditionXqls(){
		
		String xql = "select * from user where 1=1" +
				"/~ and username = #{username} ~/" +
				"/~ and age = ${age} ~/" +
				"/~ and name in #{names} ~/";
		
		List<String> conditionXqls = XqlBuilder.splitConditionXqls(xql);
		
		assertEquals(" and username = #{username} " ,conditionXqls.get(0));
		
		assertEquals(" and age = ${age} " ,conditionXqls.get(1));
		
		assertEquals(" and name in #{names} " ,conditionXqls.get(2));
	}
	
	@Test
	public void testExtracProp(){
		String xql = "and username = #{username} ";
		String prop = XqlBuilder.extracProp(xql);
		assertEquals("username" , prop);
		
		xql = " and age = ${age} ";
		prop = XqlBuilder.extracProp(xql);
		assertEquals("age" , prop);
	}
	
	@Test
	public void test(){
		String xql = "select * from user where 1=1" +
			"/~ and username = #{username} ~/" +
			"/~ and password = #{password} ~/" +
			"/~ and age = ${age} ~/" +
			"/~ and sex = ${sex} ~/" +
			"/~ and name in #{names} ~/" +
			" limit 100 ";
		
		Map<String , Object> params = new HashMap<String , Object>();
		params.put("username", "bin");
		params.put("age", 12);
		params.put("sex", "");
		List<String> names = new ArrayList<String>();
		names.add("amy");
		names.add("betty");
		params.put("names", names);

		XqlResult result = XqlBuilder.build(xql,params);
		String sql = result.getXql();
		List<Object> queryParams = result.getParams();
		
		assertEquals("select * from user where 1=1 and username = ?  and age = 12  and name in ( ? , ? )  limit 100 " , sql);
		
		assertEquals("bin" , queryParams.get(0));
		
		assertEquals("amy" , queryParams.get(1));
		
		assertEquals("betty" , queryParams.get(2));
		
	}
	
	@Test
	public void test2(){
		String xql = "select * from user where 1=1" +
			"/~ and name in ${names} ~/";
		
		Map<String , Object> params = new HashMap<String , Object>();
		List<String> names = new ArrayList<String>();
		names.add("amy");
		names.add("betty");
		params.put("names", names);

		XqlResult result = XqlBuilder.build(xql,params);
		String sql = result.getXql();
		
		assertEquals("select * from user where 1=1 and name in ('amy','betty') " , sql);
		
	}
	
	@Test
	public void testCondition(){
		
		String xql = " select * from user where 1=1 " 
				+ "/~ ${level == 'young' ? 'and age < 30' : 'and age >= 30' } ~/";
		Map<String , Object> params = new HashMap<String , Object>();
		params.put("level" , "young");
		
		XqlResult result = XqlBuilder.build(xql,params);
		String sql = result.getXql();
		
		assertEquals(" select * from user where 1=1  and age < 30 " , sql);
	}
	
}
