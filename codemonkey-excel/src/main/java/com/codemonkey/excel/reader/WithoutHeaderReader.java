package com.codemonkey.excel.reader;

import java.util.ArrayList;
import java.util.List;

import com.codemonkey.excel.handler.IDataHandler;
import com.codemonkey.utils.SysUtils;

public class WithoutHeaderReader implements IRowReader{

	List<List<String>> data;

	public WithoutHeaderReader(){}

	public WithoutHeaderReader(List<List<String>> data){
		this.data = data;
	}

	@Override
	public void getRow(int sheetIndex, int curRow, List<String> rowlist, List<String> headerRowList) {
		List<String> tempRow = new ArrayList<String>();
		if(SysUtils.isNotEmpty(rowlist)){
			for(String obj : rowlist){
				tempRow.add(obj);
			}
		}
		data.add(tempRow);
	}


	@Override
	public void processRow(List<String> rowlist, List<String> headerRowList, IDataHandler handler) {
		handler.dataProcess(rowlist);
	}

	@Override
	public int getHeaderRows() {
		return -1;
	}
}
