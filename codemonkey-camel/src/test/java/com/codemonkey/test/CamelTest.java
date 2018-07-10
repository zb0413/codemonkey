package com.codemonkey.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"classpath*:spring/applicationContext-*.xml"
})
@Transactional
@SpringBootTest
public class CamelTest {

	
	@Before
	public void startActivmq(){
		
	}
	
	@Test
	public void test(){
		
	}
}
