package com.codemonkey.test.utils;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.core.io.Resource;

import com.codemonkey.utils.ExcelUtils;
import com.codemonkey.utils.SysUtils;

public class ExcelUtilsTest {

	@Test
	public void testRead() throws IOException{
		Resource r = SysUtils.getResource("testExcel.xls");
		List<Map<String , Object>> list = ExcelUtils.readExcel(r.getInputStream());
		assertEquals(1 , list.size());
		assertEquals("20" , list.get(0).get("百分比"));
		assertEquals("100000.2222" , list.get(0).get("会计专用"));
		assertEquals("10000000" , list.get(0).get("科学计数法"));
		assertEquals("123311" , list.get(0).get("文本"));
	}
}
