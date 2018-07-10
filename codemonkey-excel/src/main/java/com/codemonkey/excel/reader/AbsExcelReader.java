package com.codemonkey.excel.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public abstract class AbsExcelReader implements IExcelReader {

	private List<List<String>> dataWithoutHeader = new ArrayList<List<String>>();
	
	private List<Map<String, String>> dataWithHeader = new ArrayList<Map<String, String>>();
	
	private IRowReader rowReader;
	
	@Override
	public List<List<String>> getDataWithoutHeader() {
		return this.dataWithoutHeader;
	}

	@Override
	public List<Map<String, String>> getDataWithHeader() {
		return this.dataWithHeader;
	}

	public IRowReader getRowReader() {
		return rowReader;
	}

	public void setRowReader(IRowReader rowReader) {
		this.rowReader = rowReader;
	}

	protected void cleanData() {
	}
}
