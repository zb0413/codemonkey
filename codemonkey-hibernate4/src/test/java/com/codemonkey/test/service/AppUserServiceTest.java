package com.codemonkey.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.codemonkey.domain.AppRole;
import com.codemonkey.domain.AppUser;
import com.codemonkey.service.AppRoleService;
import com.codemonkey.service.AppUserService;
import com.codemonkey.test.Hibernate4AppTest;

public class AppUserServiceTest extends Hibernate4AppTest {

	@Autowired
	AppUserService appUserService;
	@Autowired
	AppRoleService appRoleService;

	@Test
	public void testGetAndSave() {

		long count = appUserService.count();

		AppUser user1 = new AppUser();
		user1.setUsername("user1");
		user1.setPassword("user1");

		AppUser user2 = new AppUser();
		user2.setUsername("user2");
		user2.setPassword("user2");

		AppUser user3 = new AppUser();
		user3.setUsername("user3");
		user3.setPassword("user3");

		// test saving
		appUserService.save(user1);
		appUserService.save(user2);
		appUserService.save(user3);

		assertNotNull(user1.getId());
		assertEquals("user1", user1.getUsername());
		assertNotNull(user1.getPassword());
		assertNotNull(user2.getId());
		assertNotNull(user3.getId());

		assertEquals(count + 3, appUserService.findAll().size());
		assertEquals(count + 3, appUserService.count());

		// test deleting
		appUserService.delete(user2.getId());

		assertEquals(count + 2, appUserService.findAll().size());

		AppUser user = appUserService.findBy("username", "user1");

		assertNotNull(user);

	}

	@Test
	public void testAuth() {
		// String url = "/app/ext/fooList/index";
		// AppUser user = appUserService.findBy("username", "user");
		// appUserService.login(user.getUsername(), user.getPassword());
		//
		// AppRole appRole = appRoleService.findBy("name", "ROLE_USER");
		// AppPermission appPermission = new AppPermission("fooList:list" ,
		// url);
		// appRole.addAppPermission(appPermission);
		// appRoleService.save(appRole);
		//
		// user.addAppRole(appRole);
		// appUserService.save(user);

		// appUserService.isAuth(url);

	}

	@Test
	public void testDistinct() {
		AppRole appRole1 = new AppRole();
		appRole1.setName("角色1");
		appRoleService.save(appRole1);

		AppRole appRole2 = new AppRole();
		appRole2.setName("角色2");
		appRoleService.save(appRole2);

		AppUser user1 = new AppUser();
		user1.setUsername("user1");
		user1.setPassword("user1");
		user1.addAppRole(appRole1);
		user1.addAppRole(appRole2);
		appUserService.save(user1);

		AppUser user = appUserService.findBy("username", "user1");

		assertNotNull(user);

		AppUser user2 = appUserService.findBy("roles.name", "角色1");

		assertNotNull(user2);
		
		user1.getRoles();
		List<String> list = new ArrayList<String>();
		list.add("角色1");
		list.add("角色2");
		AppUser user3 = appUserService.findBy("username&&roles.name_IN", "user1"  , list);
		assertNotNull(user3);
	}

	@Test
	public void testVersioned() {
		AppUser user1 = new AppUser();
		user1.setUsername("user1");
		user1.setPassword("user1");

		appUserService.save(user1);
		
		Integer version = user1.getVersion();
		assertNotNull(version);

		user1.setCode("user1");
		appUserService.save(user1);
		Integer version2 = user1.getVersion();
		assertEquals(new Integer(version + 1) , version2);
		

	}

}
