package com.codemonkey.excel.reader;

import java.util.List;
import java.util.Map;

public interface IExcelReader {

	void process(int sheetIndex , IRowReader rowReader);

	List<List<String>> getDataWithoutHeader();

	List<Map<String, String>> getDataWithHeader();
	
	IRowReader getRowReader();
}
