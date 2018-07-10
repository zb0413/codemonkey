package com.codemonkey.excel.writer;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface IExcelWriter {

	void process(OutputStream outputStream , String sheetName , List<String> headers , List<Map<String , Object>> rowData);
	
	void process(OutputStream outputStream , String sheetName , List<List<?>> rowData);

}
