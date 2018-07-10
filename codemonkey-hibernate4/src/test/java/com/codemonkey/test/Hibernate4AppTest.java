package com.codemonkey.test;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"classpath*:spring/applicationContext-*.xml"
})
@Transactional
@SpringBootTest
public class Hibernate4AppTest {

	@Test
	public void test(){
		
	}
}
