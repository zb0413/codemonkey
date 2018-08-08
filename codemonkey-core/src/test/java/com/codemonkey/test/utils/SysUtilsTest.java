package com.codemonkey.test.utils;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.junit.Test;

import com.codemonkey.utils.SysUtils;

public class SysUtilsTest{

//	@Test
//	public void testColumnToProp(){
//		String prop = SysUtils.columnToProp("test_a");
//		assertEquals("testA" , prop);
//		
//		prop = SysUtils.columnToProp("test_1");
//		assertEquals("test1" , prop);
//		
//		prop = SysUtils.columnToProp("test_good_1");
//		assertEquals("testGood1" , prop);
//	}
	
//	@Test
//	public void testPropToColumn(){
//		String prop = SysUtils.propToColumn("testA");
//		assertEquals("test_a" , prop);
//		
//		prop = SysUtils.propToColumn("test1");
//		assertEquals("test_1" , prop);
//		
//		prop = SysUtils.propToColumn("testGood1");
//		assertEquals("test_good_1" , prop);
//	}
	
	@Test
	public void testFormatString(){
		String s = "hello world";
		String result = SysUtils.formatString(s);
		
		assertEquals("hello world" , result);
		
		s = "hello world {0}";
		result = SysUtils.formatString(s , "java");
		
		assertEquals("hello world java" , result);
		
		s = "hello world {0} {1}";
		result = SysUtils.formatString(s , "java");
		
		assertEquals("hello world java {1}" , result);
		
		s = "hello world {0} {1}";
		result = SysUtils.formatString(s , "java" , "ruby" , "python");
		
		assertEquals("hello world java ruby" , result);
	}
	
	@Test
	public void testFormatNumber(){
		
//		Number 1: -01235
//		Number 2: -1234.567
//		Number 3: -1235
//		Number 4: 00
//		Number 5: -.12
//		Number 6: -0.12
//		Number 7: -1234.6
//		Number 8: -1234.567
//		Number 9: -1234.567
//		Number 10: -1234.567000
//		Number 11: -1,235
//		Number 12: -1,234,568
//		Number 13: -#1235
//		Number 14: -text1235
		
		Double value = -1234.567;
		
        String number = SysUtils.formatNumber(value , "00000");
        assertEquals( "-01235" , number);
		 
        number = SysUtils.formatNumber(value , "0000.000");
        assertEquals( "-1234.567" , number);
		          
        number = SysUtils.formatNumber(value , "##");
        assertEquals( "-1235" , number);
        
        number = SysUtils.formatNumber(0 , "##00");
        assertEquals( "00" , number);
        
        number = SysUtils.formatNumber(-0.123 , ".00");
        assertEquals( "-.12" , number);
        
        number = SysUtils.formatNumber(-0.123 , "0.00");
        assertEquals( "-0.12" , number);
        
        number = SysUtils.formatNumber(value , "0.00");
        assertEquals( "-1234.57" , number);
        
        number = SysUtils.formatNumber(value , "#.######");
        assertEquals( "-1234.567" , number);
        
        number = SysUtils.formatNumber(value , ".######");
        assertEquals( "-1234.567" , number);
        
        number = SysUtils.formatNumber(value , "#.000000");
        assertEquals( "-1234.567000" , number);
        
        number = SysUtils.formatNumber(value , "#,###,###");
        assertEquals( "-1,235" , number);
        
        number = SysUtils.formatNumber(-1234567.890 , "#,###,###");
        assertEquals( "-1,234,568" , number);
        
        number = SysUtils.formatNumber(value , "'#'#");
        assertEquals( "-#1235" , number);
        
        number = SysUtils.formatNumber(value , "'text'#");
        assertEquals( "-text1235" , number);

	}
	
	@Test
	public void testExportTableDescription(){
//		String desc = SysUtils.exportTableDescription(AppUser.class);
		
	}
	
	@Test
	public void testDateUtils(){
		DateTime dateTime = new DateTime("2014-11-30");
		assertEquals( 2014 , SysUtils.year(dateTime));
		assertEquals( 11 , SysUtils.month(dateTime));
		assertEquals( 30 , SysUtils.date(dateTime));
		assertEquals( 4 , SysUtils.season(dateTime));
	}
	
	
}
