package com.codemonkey.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;

import com.codemonkey.domain.AppRole;
import com.codemonkey.domain.AppUser;
import com.codemonkey.domain.QAppUser;
import com.codemonkey.repository.AppRoleRepository;
import com.codemonkey.repository.AppUserRepository;
import com.codemonkey.repository.helper.QueryDslHelper;
import com.codemonkey.utils.ExtConstant;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringPath;

public class JsonQueryTest extends SecurityTest {

	@Autowired AppUserRepository appUserJpaRepository;
	@Autowired AppRoleRepository appRoleJpaRepository;
	
	@Qualifier("mvcConversionService")
	@Autowired ConversionService mvcConversionService;
	
	@Test
	public void testQueryPath(){
		
		QueryDslHelper helper = new QueryDslHelper();
		
		QAppUser quser = QAppUser.appUser;
		StringPath stringPath = quser.roles.any().functionNodes.any().name;
		
		EntityPathResolver resolver = SimpleEntityPathResolver.INSTANCE;
		String query = "roles.functionNodes.name";
		EntityPath<?> path = resolver.createPath(AppUser.class);
		SimpleExpression<?> p = helper.toStringPath(path , query);
		
		assertEquals(p.toString() , stringPath.toString());
	}
	
	@Test
	public void testSimpleSearch(){
		AppUser appUser1 = new AppUser();
		appUser1.setName("test1");
		appUserJpaRepository.save(appUser1);
		
		AppUser appUser2 = new AppUser();
		appUser2.setName("test2");
		appUserJpaRepository.save(appUser2);
		
		JSONObject queryInfo = new JSONObject();
		queryInfo.put("name_Like", "test1");
		
		JSONObject queryAndSort = new JSONObject();
		queryAndSort.put(ExtConstant.QUERY , queryInfo);
		
		List<AppUser> list = appUserJpaRepository.findByQueryInfo(queryAndSort);
		
		assertEquals(1 , list.size());
		assertEquals(1 , appUserJpaRepository.countByQueryInfo(queryAndSort));
		
	}
	
	@Test
	public void testComplexSearch(){
		
		prepareData();
		
//		QAppUser quser = QAppUser.appUser;
//		Predicate predicate = quser.roles.any().functionNodes.any().name.like("appRole1");
//		Iterable<AppUser> it = appUserJpaRepository.findAll(predicate);
		
		
		JSONObject queryInfo = new JSONObject();
		queryInfo.put("roles.name_Like", "appRole1");
		
		JSONObject queryAndSort = new JSONObject();
		queryAndSort.put(ExtConstant.QUERY , queryInfo);
		
		List<AppUser> list = appUserJpaRepository.findByQueryInfo(queryAndSort);
		
		assertEquals(1 , list.size());
		assertEquals(1 , appUserJpaRepository.countByQueryInfo(queryAndSort));
		
		
		//test or
		queryInfo = new JSONObject();
		queryInfo.put("roles.name_Like||roles.code_Like", "appRole");
		queryAndSort.put(ExtConstant.QUERY , queryInfo);
		
//		QAppUser quser = QAppUser.appUser;
//		Predicate predicate = quser.roles.any().code.like("appRole%");
//		Iterable<AppUser> it = appUserJpaRepository.findAll(predicate);
		
		list = appUserJpaRepository.findByQueryInfo(queryAndSort);
		
		assertEquals(1 , list.size());
		
		
	}
	
	@Test
	public void testOrderBy(){
		
		prepareData();
		//test orders
		JSONObject queryAndSort = new JSONObject();
		JSONObject sort = new JSONObject();
		sort.put("version", "desc");
		queryAndSort.put(ExtConstant.SORT , sort);
		
		List<AppUser> list = appUserJpaRepository.findByQueryInfo(queryAndSort);
		
		assertEquals(new Integer(3) , list.get(0).getVersion());
	}

	private void prepareData() {
		AppUser appUser = new AppUser();
		appUser.setName("test");
		appUser.setVersion(3);
		appUserJpaRepository.save(appUser);
		
		AppRole appRole1 = new AppRole();
		appRole1.setName("appRole1");
		appRole1.setCode("appRole-1");
		appUser.addAppRole(appRole1);
		
		AppRole appRole2 = new AppRole();
		appRole2.setName("appRole2");
		appRole2.setCode("code-2");
		appUser.addAppRole(appRole2);
		
		appRoleJpaRepository.save(appRole1);
		appRoleJpaRepository.save(appRole2);
		appUserJpaRepository.save(appUser);
	}
	
}
