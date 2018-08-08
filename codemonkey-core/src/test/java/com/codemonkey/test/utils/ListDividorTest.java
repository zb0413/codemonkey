package com.codemonkey.test.utils;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.codemonkey.utils.ListDividor;

public class ListDividorTest {

	List<Object> list;
	
	@Before
	public void before(){
		int totalSize = 100;
		list = new ArrayList<Object>();
		for(int i = 0 ; i < totalSize ; i++){
			list.add(new Object());
		}
	}
	@Test
	public void test(){
		
		int size = 5;
		ListDividor<Object> dividor = new ListDividor<Object>();
		List<List<Object>> subLists = dividor.divide(list, size);
		
		int countAfterDivide = 0;
		for(List<Object> ls : subLists){
			countAfterDivide += ls.size();
		}
		assertEquals(list.size() , countAfterDivide);
	}
	
	@Test
	public void test2(){
		
		int size = 3;
		ListDividor<Object> dividor = new ListDividor<Object>();
		List<List<Object>> subLists = dividor.divide(list, size);
		
		int countAfterDivide = 0;
		for(List<Object> ls : subLists){
			countAfterDivide += ls.size();
		}
		assertEquals(list.size() , countAfterDivide);
		assertEquals(3 , subLists.size());
		assertEquals(34 , subLists.get(subLists.size() - 1).size());
		
	}
}
