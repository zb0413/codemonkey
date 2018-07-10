package com.codemonkey.service;

import java.io.OutputStream;

public interface DataExportService {

	void export(String tableName , String sql , OutputStream out , String fileType);
	
}
