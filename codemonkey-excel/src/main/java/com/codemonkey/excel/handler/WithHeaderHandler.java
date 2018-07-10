package com.codemonkey.excel.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WithHeaderHandler extends AbsDataHandler {
    
	private List<Map<String , String>> rows = new ArrayList<Map<String , String>>();
	
    @Override
    public void dataProcess(Object row) {
    	if(row instanceof Map){
    		getRows().add((Map<String , String>)row);
    	}
    }

	public List<Map<String , String>> getRows() {
		return rows;
	}

	public void setRows(List<Map<String , String>> rows) {
		this.rows = rows;
	}
}
