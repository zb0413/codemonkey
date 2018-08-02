package com.codemonkey.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.codemonkey.domain.AppRole;
import com.codemonkey.domain.AppUser;
import com.codemonkey.repository.AppRoleRepository;
import com.codemonkey.repository.AppUserRepository;

public class StringQueryTest extends SecurityTest{

	@Autowired AppUserRepository appUserJpaRepository;
	@Autowired AppRoleRepository appRoleJpaRepository;
	
	@Test
	public void testGetAndSave() {

		long count = appUserJpaRepository.count();

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
		appUserJpaRepository.save(user1);
		appUserJpaRepository.save(user2);
		appUserJpaRepository.save(user3);

		assertNotNull(user1.getId());
		assertEquals("user1", user1.getUsername());
		assertNotNull(user1.getPassword());
		assertNotNull(user2.getId());
		assertNotNull(user3.getId());

		assertEquals(count + 3, appUserJpaRepository.count());

		// test deleting
		appUserJpaRepository.delete(user2);

		assertEquals(count + 2, appUserJpaRepository.count());

		AppUser user = appUserJpaRepository.findBy("username", "user1");

		assertNotNull(user);

	}


	@Test
	public void testDistinct() {
		AppRole appRole1 = new AppRole();
		appRole1.setName("角色1");
		appRoleJpaRepository.save(appRole1);

		AppRole appRole2 = new AppRole();
		appRole2.setName("角色2");
		appRoleJpaRepository.save(appRole2);

		AppUser user1 = new AppUser();
		user1.setUsername("user1");
		user1.setPassword("user1");
		user1.addAppRole(appRole1);
		user1.addAppRole(appRole2);
		appUserJpaRepository.save(user1);

		AppUser user = appUserJpaRepository.findBy("username", "user1");

		assertNotNull(user);

		AppUser user2 = appUserJpaRepository.findBy("roles.name", "角色1");

		assertNotNull(user2);
		
		user1.getRoles();
		List<String> list = new ArrayList<String>();
		list.add("角色1");
		list.add("角色2");
		AppUser user3 = appUserJpaRepository.findBy("username&&roles.name_IN", "user1"  , list);
		assertNotNull(user3);
	}

	@Test
	public void testVersioned() {
		AppUser user1 = new AppUser();
		user1.setUsername("user1");
		user1.setPassword("user1");

		appUserJpaRepository.save(user1);
		
		Integer version = user1.getVersion();
		assertNotNull(version);

//		assertNotNull(user1.getCreationDate());
//		assertNotNull(user1.getModificationDate());
		
//		user1.setCode("user1+1");
//		appUserJpaRepository.save(user1);
//		Integer version2 = user1.getVersion();
//		assertEquals(new Integer(version + 1) , version2);

	}
	
	@Test
	public void testUnique(){
		AppUser appUser1 = new AppUser();
		appUser1.setCode("appUser-1");
		appUser1.setName("test");
		appUser1.setVersion(3);
		assertEquals(true , appUserJpaRepository.isUnique(appUser1, "code", appUser1.getCode()));
		
		AppUser appUser2 = new AppUser();
		appUser2.setCode("appUser-2");
		appUser2.setName("test");
		assertEquals(true , appUserJpaRepository.isUnique(appUser2, "code", appUser2.getCode()));
		
		
		appUserJpaRepository.save(appUser1);
		appUserJpaRepository.save(appUser2);
		assertEquals(false , appUserJpaRepository.isUnique(appUser1, "name", appUser1.getName()));
		
		assertEquals(true , appUserJpaRepository.isUnique(appUser2, "code", appUser2.getCode()));
		
	}
	
	@Test
	public void testFindBy(){
		AppUser appUser1 = new AppUser();
		appUser1.setCode("appUser-1");
		appUser1.setName("test");
		appUser1.setVersion(3);
		appUserJpaRepository.save(appUser1);
		
		AppUser appUser2 = new AppUser();
		appUser2.setCode("appUser-2");
		appUser2.setName("test");
		appUserJpaRepository.save(appUser2);
		
		
		AppUser user = appUserJpaRepository.findBy("code", appUser1.getCode());
		assertNotNull(user);
		
		List<AppUser> list = appUserJpaRepository.findAllBy("name_Like", "test");
		assertEquals(2 , list.size());
		
		list = appUserJpaRepository.findAllBy("code&&name_Like", "appUser-1" , "test");
		assertEquals(1 , list.size());
		
		list = appUserJpaRepository.findAllBy("code_Like||name", "appUser");
		assertEquals(2 , list.size());
	
	}
	
	@Test
	public void testSumBy(){
		AppUser appUser1 = new AppUser();
		appUser1.setCode("appUser-1");
		appUser1.setName("test");
		appUser1.setVersion(3);
		appUserJpaRepository.save(appUser1);
		
		AppUser appUser2 = new AppUser();
		appUser2.setCode("appUser-2");
		appUser2.setName("test");
		appUser1.setVersion(1);
		appUserJpaRepository.save(appUser2);
		
		Double result = appUserJpaRepository.sumBy("version", "");
		assertEquals(new Double(4) , result);
		
		result = appUserJpaRepository.sumBy("version", "code" , "appUser-1");
		assertEquals(new Double(3) , result);
	}
	
	@Test
	public void testCountBy(){
		AppUser appUser1 = new AppUser();
		appUser1.setCode("appUser-1");
		appUser1.setName("test");
		appUser1.setVersion(3);
		appUserJpaRepository.save(appUser1);
		
		AppUser appUser2 = new AppUser();
		appUser2.setCode("appUser-2");
		appUser2.setName("test");
		appUser1.setVersion(1);
		appUserJpaRepository.save(appUser2);
		
		Long result = appUserJpaRepository.count();
		assertEquals(new Long(2) , result);
		
		result = appUserJpaRepository.countBy("version_GT", 1);
		assertEquals(new Long(1) , result);
	}

}
