package com.codemonkey.error;

import org.json.JSONObject;


public abstract class FieldValidation{

	public static final String NOT_DRAFT = " is not draft ";

	public static final String LESS_THAN_ZERO = " is less than 0 ";

	public static final String EMPTY = " is empty ";
	
	private String fieldName;
	
	private String message;
	
	abstract String getType();

	public JSONObject json() {
		JSONObject jo = new JSONObject();
		jo.put("type", getType());
		jo.put("fieldName" , getFieldName());
		jo.put("message" , getMessage());
		return jo;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
