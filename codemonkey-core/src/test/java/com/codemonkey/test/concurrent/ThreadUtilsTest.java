package com.codemonkey.test.concurrent;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.junit.Before;
import org.junit.Test;

import com.codemonkey.utils.SysUtils;
import com.codemonkey.utils.ThreadUtils;

public class ThreadUtilsTest {

	private List<Integer> numbers = new ArrayList<Integer>();
	private int poolSize = 10;
	private int total = 10000;
	List<Map<String , Integer>> divider = null;
	private Long expectedResult = 0L;
	List<Callable<Long>> threads = new ArrayList<Callable<Long>>(); 
	
	@Before
	public void before(){
		long r = 0L;
		for(int i = 0 ; i < total ; i++){
			numbers.add(i);
			r += i;
		}
		expectedResult = r;
		divider = SysUtils.getDivider(total, poolSize);
		
		for(Map<String , Integer> m : divider){
			SumCalculator c = new SumCalculator(numbers, m.get("start"), m.get("end"));
			threads.add(c);
		}
	}
	
	@Test
	public void testCompletionService(){
		
		ThreadUtils<Long> utils = new ThreadUtils<Long>(poolSize);
		List<Long> resultList = utils.runWithCompletionService(threads);
		Long total = 0L;
		for (Long r : resultList ) {			
			total += r;
		}
		assertEquals(expectedResult , total);
	}
	
	@Test
	public void testFutureTask(){
		ThreadUtils<Long> utils = new ThreadUtils<Long>(poolSize);
		List<Long> resultList = utils.runWithFutureTask(threads);
		Long total = 0L;
		for (Long r : resultList ) {			
			total += r;
		}
		assertEquals(expectedResult , total);
	}
	
	@Test
	public void testBlockingQueque(){
		
	}
}
