package com.codemonkey.test.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.codemonkey.utils.RegUtils;

public class RegUtilsTest {

	@Test
	public void test(){
		
		//{提取内容}
		String reg = "(?<=\\{)(.+?)(?=\\})";
		String s = "{test1} - {test2}";
		List<String> list = RegUtils.extract(reg, s);
		
		assertEquals("test1" , list.get(0));
		assertEquals("test2" , list.get(1));
		
		assertTrue(RegUtils.find(reg, s));
		
	}
	
	@Test
	public void testwoo(){
		
		//{提取内容}
		String reg = "[^39.51|^39.52]";
		String s = "39.54002";
		boolean bln = RegUtils.find(reg, s);
		
		assertTrue(bln);
		
	}
	
	@Test
	public void testMultiMatch(){
		//检测字符串里面是否同时包含“北京”,“重庆”四个名称(也可能更多个),位置可以颠倒。
		String str1 = "上海aaaaaaaaa重庆bbbbbbbbb天津ccccccccccc北京";
		String str2 = "上海天津重庆bbbbbbbbb";
		String str3 = "北京天津重庆上海";
		
		//{提取内容}
		String reg = "(?=.*?北京)(?=.*?重庆)";
		boolean bln = RegUtils.find(reg, str1);
		assertTrue(bln);
		
		bln = RegUtils.find(reg, str2);
		assertFalse(bln);
		
		bln = RegUtils.find(reg, str3);
		assertTrue(bln);
		
	}
	
	@Test
	public void testMultiMatch2(){
		String str1 = "C11.000,Z51.000";
		String str2 = "C11.000,Z51.005";
		String str3 = "C11.000";
		String str4 = "Z51.000,M23.000,C11.000";
		String str5 = "C11.900,Z51.003";
		
		//{提取内容}
		String reg = "(?=.*?(C11\\.000|C11\\.001|C11\\.100|C11\\.101|C11\\.102|C11\\.200|C11\\.201|C11\\.202|C11\\.300|C11\\.301|C11\\.302|C11\\.800|C11\\.801|C11\\.900|C11\\.901|C78\\.301|C78\\.302))(?=.*?(Z51\\.000|Z51\\.001|Z51\\.002|Z51\\.003))";
		boolean bln = RegUtils.find(reg, str1);
		assertTrue(bln);
		
		bln = RegUtils.find(reg, str2);
		assertFalse(bln);
		
		bln = RegUtils.find(reg, str3);
		assertFalse(bln);
		
		bln = RegUtils.find(reg, str4);
		assertTrue(bln);
		
		bln = RegUtils.find(reg, str5);
		assertTrue(bln);
		
	}
	
	@Test
	public void testOperationPatten(){
		String str1 = "88.5501";
		String str2 = "88.5601";
		String str3 = "88.5701";
		String str4 = "88.5801";
		String str5 = "88.5702";
		
		//{提取内容}
		String reg = "88\\.5[5-7]01";
		boolean bln = RegUtils.find(reg, str1);
		assertTrue(bln);
		
		bln = RegUtils.find(reg, str2);
		assertTrue(bln);
		
		bln = RegUtils.find(reg, str3);
		assertTrue(bln);
		
		bln = RegUtils.find(reg, str4);
		assertFalse(bln);
		
		bln = RegUtils.find(reg, str5);
		assertFalse(bln);
		
	}
	
	//不包含特定字符串
	@Test
	public void testNotInclude(){
		String reg = "^((?!(42\\.4101|42\\.4102)).)*$";
		boolean bln = RegUtils.matches(reg, "42.4101,42.4102");
		assertFalse(bln);
		
		bln = RegUtils.matches(reg, "42.4101");
		assertFalse(bln);
		
		bln = RegUtils.matches(reg, "42.4103");
		assertTrue(bln);
	}
	
	//小于14岁字符
	@Test
	public void testLessThan14(){
		
		String reg = "D\\d+|M\\d+|Y[0-9]{1}|Y1[0-4]{1}";
		boolean bln = RegUtils.matches(reg, "D1");
		assertTrue(bln);
		bln = RegUtils.matches(reg, "M24");
		assertTrue(bln);
		
		bln = RegUtils.matches(reg, "Y14");
		assertTrue(bln);
		
		bln = RegUtils.matches(reg, "Y9");
		assertTrue(bln);
		
		bln = RegUtils.matches(reg, "Y15");
		assertFalse(bln);
	}
	
	@Test
	public void testExtract(){
		 List<String> list = RegUtils.extract("\\d{1,2}" , "(12) 汕大附十方哥");
		 assertEquals("12" , list.get(0));
	}
	
}
