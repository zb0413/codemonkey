package com.codemonkey.excel.reader;

import com.codemonkey.excel.handler.IDataHandler;
import com.codemonkey.utils.SysUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WithHeaderReader implements IRowReader{

	private List<Map<String , String>> data;
	
	private int headerRows = 1;

	public  WithHeaderReader(){}
	
	public  WithHeaderReader(int headerRows){
		this.headerRows = headerRows;
	}

	@Override
	public void getRow(int sheetIndex, int curRow, List<String> rowlist, List<String> headerRowList) {
		if(curRow != 0){
			if(SysUtils.isNotEmpty(headerRowList)){
				Map<String , String> map = new HashMap<String , String>();
				for(int i = 0 ; i < headerRowList.size() ; i++){
					if(i > rowlist.size() - 1){
						map.put(headerRowList.get(i), null);
					}else{
						map.put(headerRowList.get(i), rowlist.get(i));
					}
				}
				data.add(map);
			}	
		}
		
	}

	public void processRow( List<String> rowlist, List<String> headerRowList, IDataHandler handler){
		if(SysUtils.isNotEmpty(headerRowList)){
			Map<String , String> map = new HashMap<String , String>();
			for(int i = 0 ; i < headerRowList.size() ; i++){
				if(i > rowlist.size() - 1){
					map.put(headerRowList.get(i), null);
				}else{
					map.put(headerRowList.get(i), rowlist.get(i));
				}
			}
			handler.dataProcess(map);
		}
	}

	@Override
	public int getHeaderRows() {
		return headerRows;
	}

}
