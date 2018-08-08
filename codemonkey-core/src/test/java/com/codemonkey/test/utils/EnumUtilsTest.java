package com.codemonkey.test.utils;

import static org.junit.Assert.assertEquals;

import org.json.JSONObject;
import org.junit.Test;

import com.codemonkey.utils.EnumUtils;

public class EnumUtilsTest {

	@Test
	public void testGettingEnumData(){
		JSONObject jo = EnumUtils.getEnmuDataByClazz("Status" , "com.codemonkey.domain" , null);
		assertEquals(2 , jo.getJSONArray("data").length());
		
	}
}
