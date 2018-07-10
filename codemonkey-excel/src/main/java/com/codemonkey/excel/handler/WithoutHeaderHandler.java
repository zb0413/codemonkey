package com.codemonkey.excel.handler;

import java.util.ArrayList;
import java.util.List;

public class WithoutHeaderHandler extends AbsDataHandler {
    
	private List<List<String>> rows = new ArrayList<List<String>>();
	
    @Override
    public void dataProcess(Object row) {
    	if(row instanceof List){
    		getRows().add((List<String>)row);
    	}
    }

	public List<List<String>> getRows() {
		return rows;
	}

	public void setRows(List<List<String>> rows) {
		this.rows = rows;
	}

}
