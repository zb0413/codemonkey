package com.codemonkey.error;

import org.json.JSONObject;



public class RowFieldValidation extends FieldValidation{

	private String rowType;
	
	public RowFieldValidation(String fieldName, String message , String rowType) {
		this.setFieldName(fieldName);
		this.setMessage(message);
		this.rowType = rowType;
	}

	@Override
	String getType() {
		return "RowField";
	}

	public JSONObject json() {
		JSONObject jo = super.json();
		jo.put("rowType", getRowType());
		return jo;
	}

	public String getRowType() {
		return rowType;
	}

	public void setRowType(String rowType) {
		this.rowType = rowType;
	}
	
}
