package com.codemonkey.test.mybatis;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.codemonkey.mybatis.service.MybatisService;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(locations = { 
	"classpath*:spring/applicationContext-*.xml"
})
@ActiveProfiles("test")
@Transactional
public class MybatisServiceTest{

	@Autowired private MybatisService mybatisService;

	@Test
	public void testQuery() throws ParseException{
		
		long count = mybatisService.count("selectAppUserList_count", null);
		
		Map<String , Object> params = new HashMap<String , Object>();
		params.put("query_username", "adm");
		long count2 = mybatisService.count("selectAppUserList_count", params);
		
		prepareData();
		
		List<Map<String , Object>> list = mybatisService.query("selectAppUserList", null);
		assertEquals(count + 2 , list.size());
		
		list = mybatisService.query("selectAppUserList", params);
		assertEquals(count2 + 1 , list.size());
		
//		params = new HashMap<String , Object>();
//		JSONArray sort = new JSONArray("[{\"property\":\"username\",\"direction\":\"ASC\"}]");
//		
//		list = mybatisService.query("selectAppUserList", queryAndSort);
//		assertEquals(4 , list.size());
//		
//		assertEquals("admin" , list.get(0).get("username"));
		
	}

	private void prepareData() {
		
		Map<String , Object> params = new HashMap<String , Object>();
		params.put("id", -1);
		params.put("username", "admin");
		params.put("password", "admin");
		mybatisService.insert("insertTestUser", params);
		
		params = new HashMap<String , Object>();
		params.put("id", -2);
		params.put("username", "user");
		params.put("password", "user");
		mybatisService.insert("insertTestUser", params);
	}
	
	@Test
	public void testCount(){

		long count = mybatisService.count("selectAppUserList_count", null);
		
		prepareData();
		
		long count2 = mybatisService.count("selectAppUserList_count", null);
		assertEquals(count + 2 , count2);
		
	}
}
