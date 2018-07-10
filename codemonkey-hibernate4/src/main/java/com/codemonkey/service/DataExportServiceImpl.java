package com.codemonkey.service;

import java.io.OutputStream;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemonkey.utils.SysUtils;

@Service
public class DataExportServiceImpl implements DataExportService{

	@Autowired
	@Resource(name="dataSource")
	private DataSource dataSource;
	
	public void export(String tableName , String sql , OutputStream out , String fileType){
		SysUtils.exportDataToFile(dataSource, tableName, sql, out, fileType);
	}
}
