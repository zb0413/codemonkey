package com.codemonkey.test.utils;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.springframework.core.io.Resource;

import com.codemonkey.utils.CsvUtils;
import com.codemonkey.utils.SysUtils;

public class CsvUtilsTest {

	@Test
	public void testReadCsv() throws IOException{
		Resource r = SysUtils.getResource("countries.test.csv");
		List<String[]> list = CsvUtils.readeCsv(r.getFile().getAbsolutePath());
		assertEquals(12 , list.size());
	}
}
