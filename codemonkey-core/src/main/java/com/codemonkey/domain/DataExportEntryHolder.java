package com.codemonkey.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.codemonkey.utils.SysUtils;

public class DataExportEntryHolder {

	private static List<DataExportEntry> holder = new ArrayList<DataExportEntry>();
	
	public static void add(DataExportEntry dataExportEntry){
		
		if(holder == null){
			holder = new ArrayList<DataExportEntry>();
		}
		
		holder.add(dataExportEntry);
	}
	
	public static List<DataExportEntry> getAll(){
		
		if(holder == null){
			return null;
		}
		
		Collections.sort(holder, new Comparator <DataExportEntry>(){
            public int compare(DataExportEntry obj1 , DataExportEntry obj2) {
            	return obj1.getOrderNo() - obj2.getOrderNo();
            }
        });
		
		return holder;
	}

	public static DataExportEntry get(String tableName) {
		if(holder == null || SysUtils.isEmpty(tableName)){
			return null;
		}
		
		for(DataExportEntry entry : holder){
			if(tableName.equals(entry.getTableName())){
				return entry;
			}
		}
		return null;
	}
	
}
