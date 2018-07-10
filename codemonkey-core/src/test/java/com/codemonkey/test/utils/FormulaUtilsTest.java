package com.codemonkey.test.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.codemonkey.utils.FormulaUtils;

//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//import java.math.RoundingMode;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.Test;
//
//import com.codemonkey.utils.FormulaUtils;

public class FormulaUtilsTest {

	@Test
	public void testCalculate() {
		// simple formula
		// result = t1 + t2
		String expr = "t1 + t2";
		Map<String, Number> params = new HashMap<String, Number>();
		params.put("t1", 5);
		params.put("t2", 7);
		assertEquals(new Integer(12), FormulaUtils.calculate(expr, params));

		// params.put("t1", null);
		// params.put("t2", 7);
		// assertNull(FormulaUtils.calculate(expr, params));

		// complex formula
		// result = {test} + 2
		// test = t1 + t2
		// expr = "{test} + 2";
		// params = new HashMap<String , Number>();
		// params.put("t1", 5);
		// params.put("t2", 7);
		// Map<String , String> formulaMap = new HashMap<String , String>();
		// formulaMap.put("test", "t1 + t2");
		// assertEquals(new Integer(14) , FormulaUtils.calculate(expr, params ,
		// formulaMap));

		// complex formula
		// result = {test} + 2
		// test = {t1} + t2
		// t1 = 2 * t2
		// params = new HashMap<String , Number>();
		// params.put("t1", 5);
		// params.put("t2", 7);
		// formulaMap.put("test", "{t1} + t2");
		// formulaMap.put("t1", "2 * t2");
		// assertEquals(new Integer(23) , FormulaUtils.calculate(expr, params ,
		// formulaMap));

		// expr = "{test} + 2 /t3";
		// FormulaUtils.calculate(expr, params , formulaMap);
	}
	
	@Test
	public void testCalculateGE() {
		String expr = "t1 + t2 >= t3 ? t1 + t2 : t3";
		Map<String, Number> params = new HashMap<String, Number>();
		params.put("t1", 5);
		params.put("t2", 7);
		params.put("t3", 9);
		assertEquals(new Integer(12), FormulaUtils.calculate(expr, params));


	}
	
	@Test
	public void testCalculateLE() {
		String expr = "t1 + t2 <= t3 ? t1 + t2 : t3";
		Map<String, Number> params = new HashMap<String, Number>();
		params.put("t1", 5);
		params.put("t2", 7);
		params.put("t3", 9);
		assertEquals(new Integer(9), FormulaUtils.calculate(expr, params));


	}
	
	@Test
	public void testCalculateEQ() {
		String expr = "t1 + t2 == t3 ? t1 : t3";
		Map<String, Number> params = new HashMap<String, Number>();
		params.put("t1", 5);
		params.put("t2", 7);
		params.put("t3", 12);
		assertEquals(new Integer(5), FormulaUtils.calculate(expr, params));


	}
	
	@Test
	public void testCalculateSpe() {
		String expr = "收入预算执行偏差<=5&&收入预算执行偏差>=-5?0:收入预算执行偏差";
		Map<String, Number> params = new HashMap<String, Number>();
		params.put("收入预算执行偏差", 3);

		assertEquals(new Integer(0), FormulaUtils.calculate(expr, params));


	}


	@Test
	public void testExtractParams() {
		String f = "t1 +  T2";
		List<String> params = FormulaUtils.extractParmas(f);
		assertEquals(2, params.size());
		assertTrue(params.contains("t1"));
		assertTrue(params.contains("T2"));

		f = "t1 - T2";
		params = FormulaUtils.extractParmas(f);
		assertEquals(2, params.size());
		assertTrue(params.contains("t1"));
		assertTrue(params.contains("T2"));

		f = "t1 * T2";
		params = FormulaUtils.extractParmas(f);
		assertEquals(2, params.size());
		assertTrue(params.contains("t1"));
		assertTrue(params.contains("T2"));

		f = "t1 / T2";
		params = FormulaUtils.extractParmas(f);
		assertEquals(2, params.size());
		assertTrue(params.contains("t1"));
		assertTrue(params.contains("T2"));

		f = "t1 + ( T2) ";
		params = FormulaUtils.extractParmas(f);
		assertEquals(2, params.size());
		assertTrue(params.contains("t1"));
		assertTrue(params.contains("T2"));

		f = "t1 + (2) * ( T2) ";
		params = FormulaUtils.extractParmas(f);
		assertEquals(2, params.size());
		assertTrue(params.contains("t1"));
		assertTrue(params.contains("T2"));

		// TODO 不能正确拆解括号
		// f = "t1 + ((2) * ( T2))";
		// params = FormulaUtils.extractParmas(f);
		// assertEquals(2 , params.size());
		// assertTrue(params.contains("t1"));
		// assertTrue(params.contains("T2"));

		// TODO 不能正确拆解三元表达式
		// f = "t1 > 0 ? t1 : 0";
		// params = FormulaUtils.extractParmas(f);
		// assertEquals(1 , params.size());
		// assertTrue(params.contains("t1"));
	}

	@Test
	public void testSplitAllFormula() {
		String f = "t1 +  T2";
		List<String> itemNameList = new ArrayList<String>();
		FormulaUtils.splitAllFormula(f, itemNameList);
		assertEquals(2, itemNameList.size());
		assertEquals("t1", itemNameList.get(0));
		assertEquals("T2", itemNameList.get(1));

		f = "t1 - T2";
		itemNameList = new ArrayList<String>();
		FormulaUtils.splitAllFormula(f, itemNameList);
		assertEquals(2, itemNameList.size());
		assertEquals("t1", itemNameList.get(0));
		assertEquals("T2", itemNameList.get(1));

		f = "t1 * T2";
		itemNameList = new ArrayList<String>();
		FormulaUtils.splitAllFormula(f, itemNameList);
		assertEquals(2, itemNameList.size());
		assertEquals("t1", itemNameList.get(0));
		assertEquals("T2", itemNameList.get(1));

		f = "t1 / T2";
		itemNameList = new ArrayList<String>();
		FormulaUtils.splitAllFormula(f, itemNameList);
		assertEquals(2, itemNameList.size());
		assertEquals("t1", itemNameList.get(0));
		assertEquals("T2", itemNameList.get(1));

		f = "t1 + ( T2) ";
		itemNameList = new ArrayList<String>();
		FormulaUtils.splitAllFormula(f, itemNameList);
		assertEquals(2, itemNameList.size());
		assertEquals("t1", itemNameList.get(0));
		assertEquals("T2", itemNameList.get(1));

		f = "t1 + (2) * ( T2) ";
		itemNameList = new ArrayList<String>();
		FormulaUtils.splitAllFormula(f, itemNameList);
		assertEquals(2, itemNameList.size());
		assertEquals("t1", itemNameList.get(0));
		assertEquals("T2", itemNameList.get(1));
		//
		// TODO 不能正确拆解括号
		f = "t1 + ((2) * ( T2))";
		itemNameList = new ArrayList<String>();
		FormulaUtils.splitAllFormula(f, itemNameList);
		assertEquals(2, itemNameList.size());
		assertEquals("t1", itemNameList.get(0));
		assertEquals("T2", itemNameList.get(1));

		f = "t1 >= 0 ? t1 : 0";
		itemNameList = new ArrayList<String>();
		FormulaUtils.splitAllFormula(f, itemNameList);
		assertEquals(1, itemNameList.size());
		assertEquals("t1", itemNameList.get(0));
		
		
		f = "t1 <= 0 ? (t1+T2) : 0";
		itemNameList = new ArrayList<String>();
		FormulaUtils.splitAllFormula(f, itemNameList);
		assertEquals(2, itemNameList.size());
		assertEquals("t1", itemNameList.get(0));
		assertEquals("T2", itemNameList.get(1));
		
		
		f = "t1 != 0 ? (t1+T2) : 0";
		itemNameList = new ArrayList<String>();
		FormulaUtils.splitAllFormula(f, itemNameList);
		assertEquals(2, itemNameList.size());
		assertEquals("t1", itemNameList.get(0));
		assertEquals("T2", itemNameList.get(1));
		
		
		f = "(t1 != 0) && (T2 < 0) || (t1 == T2) ? (t1+T2) : 0";
		itemNameList = new ArrayList<String>();
		FormulaUtils.splitAllFormula(f, itemNameList);
		assertEquals(2, itemNameList.size());
		assertEquals("t1", itemNameList.get(0));
		assertEquals("T2", itemNameList.get(1));
		
		
		f = "!(t1 == T2)";
		itemNameList = new ArrayList<String>();
		FormulaUtils.splitAllFormula(f, itemNameList);
		assertEquals(2, itemNameList.size());
		assertEquals("t1", itemNameList.get(0));
		assertEquals("T2", itemNameList.get(1));


	}

	@Test
	public void testRounding() {
		String expr = "t1 / t2";
		Map<String, Number> params = new HashMap<String, Number>();
		params.put("t1", 10d);
		params.put("t2", 3);
		assertEquals(new Double(3.33),
				FormulaUtils.calculate(expr, params, 2, RoundingMode.HALF_UP));

		params.put("t1", 13d);
		params.put("t2", 7);
		assertEquals(new Double(1.9),
				FormulaUtils.calculate(expr, params, 1, RoundingMode.HALF_UP));

	}
	
//	@Test
//	public void testRounding1() {
//		String expr = "t1 / t2";
//		Map<String, Number> params = new HashMap<String, Number>();
//		params.put("t1", 0.39);
//		params.put("t2", 0.4);
//		assertEquals(0.98 , FormulaUtils.calculate(expr, params, 2, RoundingMode.HALF_UP));
//	}

}
