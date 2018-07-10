package com.codemonkey.test.excel;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.codemonkey.excel.handler.CounterHandler;
import com.codemonkey.excel.handler.IDataHandler;
import com.codemonkey.excel.handler.WithHeaderHandler;
import com.codemonkey.excel.handler.WithoutHeaderHandler;
import com.codemonkey.excel.reader.Excel2003Reader;
import com.codemonkey.excel.reader.Excel2007Reader;
import com.codemonkey.excel.reader.IExcelReader;
import com.codemonkey.excel.reader.WithHeaderReader;
import com.codemonkey.excel.reader.WithoutHeaderReader;
import com.codemonkey.excel.writer.Excel2003Writer;
import com.codemonkey.excel.writer.Excel2007Writer;
import com.codemonkey.excel.writer.IExcelWriter;
import com.codemonkey.test.excel.handlers.AvgInHosDaysHandler;
import com.codemonkey.utils.SysUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(locations = { 
	"classpath*:spring/applicationContext-*.xml"
})
@Transactional
public class ExcelHelperTest{

	
	private String withoutHeader2003Test = "withoutHeader2003Test.xls";
	
	private String withHeader2003Test = "withHeader2003Test.xls";
	
	private String withoutHeader2007Test = "withoutHeader2007Test.xlsx";
	
	private String withHeader2007Test = "withHeader2007Test.xlsx";
	
	
	@Before
	public void beforeTest(){
		List<String> list = new ArrayList<String>();
		list.add(withoutHeader2003Test);
		list.add(withHeader2003Test);
		list.add(withoutHeader2007Test);
		list.add(withHeader2007Test);
		
		for(String p : list){
			File f = new File(p);
			if(f.exists()){
				f.delete();
			}
		}
	}
	
	@Test
	public void testReadWithoutHeader2003() throws IOException{
		
		CounterHandler counterHandler = new CounterHandler();
		
		Resource r = SysUtils.getResource("classpath:test2003.xls");
		InputStream inputStream = r.getInputStream();
		Set<IDataHandler> set = new HashSet<IDataHandler>();
		set.add(counterHandler);
		IExcelReader reader = new Excel2003Reader(inputStream,set);
		reader.process(0 , new WithoutHeaderReader());
		assertEquals(new Double(11) , counterHandler.getResult());
	}

	@Test
	public void testReadWithHeader2003() throws IOException{
		
		CounterHandler counterHandler = new CounterHandler();
		
		Resource r = SysUtils.getResource("classpath:test2003.xls");
		InputStream inputStream = r.getInputStream();
		Set<IDataHandler> set = new HashSet<IDataHandler>();
		set.add(counterHandler);
		IExcelReader reader = new Excel2003Reader(inputStream,set);
		reader.process(0 , new WithHeaderReader());
		assertEquals(new Double(10) , counterHandler.getResult());
	}

	@Test
	public void testReadWithoutHeader2007() throws IOException{
		
		CounterHandler counterHandler = new CounterHandler();
		
		Resource r = SysUtils.getResource("classpath:test2007.xlsx");
		InputStream inputStream = r.getInputStream();
		Set<IDataHandler> set = new HashSet<IDataHandler>();
		set.add(counterHandler);
		IExcelReader reader = new Excel2007Reader(inputStream,set);
		reader.process(0 , new WithoutHeaderReader());
		assertEquals(new Double(11) , counterHandler.getResult());
	}

	@Test
	public void testReadWithHeader2007() throws IOException{
		
		CounterHandler counterHandler = new CounterHandler();
		
		Resource r = SysUtils.getResource("classpath:test2007.xlsx");
		InputStream inputStream = r.getInputStream();
		Set<IDataHandler> set = new HashSet<IDataHandler>();
		set.add(counterHandler);
		IExcelReader reader = new Excel2007Reader(inputStream,set);
		reader.process(0 , new WithHeaderReader());
		assertEquals(new Double(10) , counterHandler.getResult());
	}

	@Test
	public void testReadLargeData2003() throws IOException{
		
		CounterHandler counterHandler = new CounterHandler();
		
		Resource r = SysUtils.getResource("classpath:excel2003_large.xls");
		InputStream inputStream = r.getInputStream();
		Set<IDataHandler> set = new HashSet<IDataHandler>();
		set.add(counterHandler);
		IExcelReader reader = new Excel2003Reader(inputStream,set);
		reader.process(0 , new WithHeaderReader());
		assertEquals(new Double(65533) , counterHandler.getResult());
	}
	
	@Test
	public void testReadLargeData2007() throws IOException {
		
		CounterHandler counterHandler = new CounterHandler();
		
		Resource r = SysUtils.getResource("classpath:excel2007_large1.xlsx");
		InputStream inputStream = r.getInputStream();
		Set<IDataHandler> set = new HashSet<IDataHandler>();
		set.add(counterHandler);
		IExcelReader reader = new Excel2007Reader(inputStream,set);
		reader.process(0 , new WithHeaderReader());
		assertEquals(new Double(65533) , counterHandler.getResult());
	}

	@Test
	public void testWriteWithHeaderData2003() throws FileNotFoundException {
		
		OutputStream out = new FileOutputStream(withHeader2003Test);
		int count = 65534;
		
		IExcelWriter writer = new Excel2003Writer();
		writer.process(out , "结果" , getHeaders() , getRowMapList(count));
		
		InputStream inputStream = new FileInputStream(withHeader2003Test);
		CounterHandler counterHandler = new CounterHandler();
		Set<IDataHandler> set = new HashSet<IDataHandler>();
		set.add(counterHandler);
		IExcelReader reader = new Excel2003Reader(inputStream , set);
		reader.process(0 , new WithHeaderReader());
		assertEquals(new Double(count) , counterHandler.getResult());
		
	}
	
	@Test
	public void testWriteWithoutHeaderData2003() throws FileNotFoundException {
		
		OutputStream out = new FileOutputStream(withoutHeader2003Test);
		int count = 65534;
		
		IExcelWriter writer = new Excel2003Writer();
		writer.process(out,"结果", getRowData(count));
		
		InputStream inputStream = new FileInputStream(withoutHeader2003Test);
		CounterHandler counterHandler = new CounterHandler();
		Set<IDataHandler> set = new HashSet<IDataHandler>();
		set.add(counterHandler);
		IExcelReader reader = new Excel2003Reader(inputStream , set);
		reader.process(0 , new WithoutHeaderReader());
		assertEquals(new Double(count) , counterHandler.getResult());
		
		
	}

	@Test
	public void testWriteWithHeaderData2007() throws FileNotFoundException {
		
		OutputStream out = new FileOutputStream(withHeader2007Test);
		int count = 5000;
		
		IExcelWriter writer = new Excel2007Writer();
		writer.process(out ,"结果", getHeaders() , getRowMapList(count));
		
		InputStream inputStream = new FileInputStream(withHeader2007Test);
		CounterHandler counterHandler = new CounterHandler();
		Set<IDataHandler> set = new HashSet<IDataHandler>();
		set.add(counterHandler);
		IExcelReader reader = new Excel2007Reader(inputStream , set);
		reader.process(0 , new WithHeaderReader());
		assertEquals(new Double(count) , counterHandler.getResult());
	}
	
	@Test
	public void testWriteWitouthHeaderData2007() throws FileNotFoundException {
		
		OutputStream out = new FileOutputStream(withoutHeader2007Test);
		int count = 110000;
		
		IExcelWriter writer = new Excel2007Writer();
		writer.process(out ,"结果", getRowData(count));
		
		InputStream inputStream = new FileInputStream(withoutHeader2007Test);
		CounterHandler counterHandler = new CounterHandler();
		Set<IDataHandler> set = new HashSet<IDataHandler>();
		set.add(counterHandler);
		IExcelReader reader = new Excel2007Reader(inputStream , set);
		reader.process(0 , new WithoutHeaderReader());
		assertEquals(new Double(count) , counterHandler.getResult());
	}

	private List<String> getHeaders(){
		List<String> headers = new ArrayList<String>();
		headers.add("姓名");
		headers.add("性别");
		headers.add("手机");
		headers.add("fb1");
		headers.add("fb2");
		return headers;
	}
	
	private List<Map<String,Object>> getRowMapList(int cnt){
		List<Map<String,Object>> rowData = new ArrayList<Map<String,Object>>();
		for(int i = 0 ; i < cnt ; i++){
			List<String> headers = getHeaders();
			Map<String,Object> m = new HashMap<String,Object>();
			for(String h : headers){
				m.put(h, h + i);
			}
			rowData.add(m);
		}
		return rowData;
	}

	private List<List<?>> getRowData(int cnt){
		List<List<?>> rowData = new ArrayList<List<?>>();
		for(int i = 0;i<cnt;i++){
			List<Object> row = new ArrayList<Object>();
			row.add("敖庆宏"+i);
			row.add("男"+i);
			row.add("18889400809"+i);
			row.add("哈哈"+i);
			row.add("呵呵"+i);
			rowData.add(row);
		}
		return rowData;
	}
	
	
	@Test
	public void testDataHandler() throws IOException{
		
		IDataHandler counterHandler = new AvgInHosDaysHandler();
		
		Resource r = SysUtils.getResource("classpath:test2007.xlsx");
		InputStream inputStream = r.getInputStream();
		Set<IDataHandler> set = new HashSet<IDataHandler>();
		set.add(counterHandler);
		IExcelReader reader = new Excel2007Reader(inputStream,set);
		reader.process(0 , new WithHeaderReader());
		assertEquals(new Double(30.9) , counterHandler.getResult());
		
	}
	
	@Test
	public void test2007EmptyColHandler() throws IOException{
		
		WithHeaderHandler handler = new WithHeaderHandler();
		
		Resource r = SysUtils.getResource("classpath:testEmptyCol.xlsx");
		InputStream inputStream = r.getInputStream();
		Set<IDataHandler> set = new HashSet<IDataHandler>();
		set.add(handler);
		IExcelReader reader = new Excel2007Reader(inputStream,set);
		reader.process(0 , new WithHeaderReader());
		assertEquals("1" , handler.getRows().get(0).get("备注"));
		
	}
	
	@Test
	public void test2003EmptyColHandler() throws IOException{
		
		WithHeaderHandler handler = new WithHeaderHandler();
		
		Resource r = SysUtils.getResource("classpath:testEmptyCol.xls");
		InputStream inputStream = r.getInputStream();
		Set<IDataHandler> set = new HashSet<IDataHandler>();
		set.add(handler);
		IExcelReader reader = new Excel2003Reader(inputStream,set);
		reader.process(0 , new WithHeaderReader());
		assertEquals("1" , handler.getRows().get(0).get("备注"));
		
	}
	
	
	@Test
	public void test2007EmptyColWithoutHeaderHandler() throws IOException{
		
		WithoutHeaderHandler handler = new WithoutHeaderHandler();
		
		Resource r = SysUtils.getResource("classpath:testEmptyColWithoutHeader.xlsx");
		InputStream inputStream = r.getInputStream();
		Set<IDataHandler> set = new HashSet<IDataHandler>();
		set.add(handler);
		IExcelReader reader = new Excel2007Reader(inputStream,set);
		reader.process(0 , new WithoutHeaderReader());
		assertEquals("1" , handler.getRows().get(1).get(11));
		
	}
	
	@Test
	public void test2003EmptyWithoutHeaderColHandler() throws IOException{
		
		WithoutHeaderHandler handler = new WithoutHeaderHandler();
		
		Resource r = SysUtils.getResource("classpath:testEmptyColWithoutHeader.xls");
		InputStream inputStream = r.getInputStream();
		Set<IDataHandler> set = new HashSet<IDataHandler>();
		set.add(handler);
		IExcelReader reader = new Excel2003Reader(inputStream,set);
		reader.process(0 , new WithoutHeaderReader());
		assertEquals("1" , handler.getRows().get(1).get(11));
	}
}
