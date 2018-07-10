package com.codemonkey.test.service;

//import java.io.FileOutputStream;

import java.io.FileOutputStream;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.codemonkey.service.DataExportService;
import com.codemonkey.test.Hibernate4AppTest;

public class DataExportServiceTest extends Hibernate4AppTest{

	@Autowired private DataExportService dataExportService;
	
	@Test
	public void test(){
		
		String tableName = "ui_portlet";
		try{
			dataExportService.export(tableName, null, new FileOutputStream(tableName + ".xml"), "xml");
			dataExportService.export(tableName, null, new FileOutputStream(tableName + ".xls"), "xls");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
