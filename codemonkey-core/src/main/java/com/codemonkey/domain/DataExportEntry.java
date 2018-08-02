package com.codemonkey.domain;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DataExportEntry extends AbsEE{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static String PREFIX = "-default-data-";

	private String tableName;
	
	private String sqlQquery;
	
	private Integer orderNo = 10;
	
	DataExportEntry(){}
	
	public DataExportEntry(String tableName){
		this.setTableName(tableName);
	}
	
	public DataExportEntry(String tableName , String sqlQquery){
		this(tableName);
		this.sqlQquery = sqlQquery;
	}
	
	public DataExportEntry(String tableName , String sqlQquery , Integer orderNo){
		this(tableName , sqlQquery);
		this.setOrderNo(orderNo);
	}
	
	public String getExportFileName(String subfix){
		return orderNo + PREFIX + tableName + "." + subfix;
	}
	
}
