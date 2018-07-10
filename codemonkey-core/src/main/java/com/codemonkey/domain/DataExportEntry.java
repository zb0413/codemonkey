package com.codemonkey.domain;

import javax.persistence.Entity;

import org.json.JSONObject;

import com.codemonkey.utils.OgnlUtils;

@Entity
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
	
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("tableName", OgnlUtils.stringValue("tableName", this));
		jo.put("sql", OgnlUtils.stringValue("sql", this));
		jo.put("orderNo", OgnlUtils.stringValue("orderNo", this));
		return jo;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getSqlQquery() {
		return sqlQquery;
	}

	public void setSqlQquery(String sqlQquery) {
		this.sqlQquery = sqlQquery;
	}
	
}
